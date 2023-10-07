package com.sewon.healthmonitor.service.ble

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.icu.util.Calendar
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.sewon.healthmonitor.R
import com.sewon.healthmonitor.service.algorithm.sleep.TopperData
import com.sewon.healthmonitor.data.model.toLocal
import com.sewon.healthmonitor.data.repository.repointerface.ISessionRepository
import com.sewon.healthmonitor.data.repository.repointerface.ITopperRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.util.ArrayDeque
import java.util.Date
import javax.inject.Inject


/**
 * create notification and queue serial data while activity is not in the foreground
 * use listener chain: SerialSocket -> SerialService -> UI fragment
 */
@AndroidEntryPoint
class BleHandleService : Service(), SerialListener {

  private val job = SupervisorJob()
  private val scope = CoroutineScope(Dispatchers.IO + job)


  @Inject
  lateinit var topperRepository: ITopperRepository

  @Inject
  lateinit var sessionRepository: ISessionRepository

  inner class SerialBinder : Binder() {
    val service: BleHandleService
      get() = this@BleHandleService
  }

  private enum class QueueType {
    Connect, ConnectError, Read, IoError
  }

  private class QueueItem {
    var type: QueueType
    var datas: ArrayDeque<ByteArray>? = null
    var e: Exception? = null

    constructor(type: QueueType) {
      this.type = type
      if (type == QueueType.Read) init()
    }

    constructor(type: QueueType, e: Exception?) {
      this.type = type
      this.e = e
    }

    constructor(type: QueueType, datas: ArrayDeque<ByteArray>?) {
      this.type = type
      this.datas = datas
    }

    fun init() {
      datas = ArrayDeque()
    }

    fun add(data: ByteArray) {
      datas!!.add(data)
    }
  }

  private val mainLooper: Handler
  private val binder: IBinder
  private val queue1: ArrayDeque<QueueItem>
  private val queue2: ArrayDeque<QueueItem>
  private val lastRead: QueueItem
  private var socket: SerialSocket? = null
  var bleDataListener: BleDataListener? = null
  private var connected = false


  init {
    mainLooper = Handler(Looper.getMainLooper())
    binder = SerialBinder()
    queue1 = ArrayDeque()
    queue2 = ArrayDeque()
    lastRead = QueueItem(QueueType.Read)
  }


  override fun onDestroy() {
    cancelNotification()
    disconnectBluetoothSocket()
    super.onDestroy()
  }

  override fun onBind(intent: Intent): IBinder {
    return binder
  }

  @Throws(IOException::class)
  fun connect(socket: SerialSocket) {
    socket.connect(this)
    this.socket = socket
    connected = true
  }

  fun disconnectBluetoothSocket() {
    connected = false // ignore data,errors while disconnecting
    cancelNotification()
    if (socket != null) {
      socket!!.disconnect()
      socket = null
    }
  }

  @Throws(IOException::class)
  fun write(data: ByteArray?) {
    if (!connected) throw IOException("not connected")
    if (data != null) {
      socket!!.write(data)
    }
  }

  fun attach(listener: BleDataListener) {
    require(Looper.getMainLooper().thread === Thread.currentThread()) { "not in main thread" }
    cancelNotification()
    // use synchronized() to prevent new items in queue2
    // new items will not be added to queue1 because mainLooper.post and attach() run in main thread
    synchronized(this) { this.bleDataListener = listener }
    for (item in queue1) {
      when (item.type) {
        QueueType.Connect -> listener.onSerialConnect()
        QueueType.ConnectError -> item.e?.let { listener.onSerialConnectError(it) }
        QueueType.Read -> item.datas?.let { listener.onSerialRead(it) }
        QueueType.IoError -> item.e?.let { listener.onSerialIoError(it) }
      }
    }
    for (item in queue2) {
      when (item.type) {
        QueueType.Connect -> listener.onSerialConnect()
        QueueType.ConnectError -> item.e?.let { listener.onSerialConnectError(it) }
        QueueType.Read -> item.datas?.let { listener.onSerialRead(it) }
        QueueType.IoError -> item.e?.let { listener.onSerialIoError(it) }
      }
    }
    queue1.clear()
    queue2.clear()
  }

  var sessionId = 0
  var pickerEndTime: Date = Date()

  fun updateCurrentSessionRefValue(refHRV: Double, refHR: Double, refBR: Double) {
    scope.launch {
      sessionRepository.updateSessionRefValue(sessionId, refHRV, refHR, refBR)
      Timber.tag("Timber").d("updateSessionRefValue")
    }
  }


  fun insertNewTopperToDatabase(topperData: TopperData) {
    scope.launch {
      val localTopper = topperData.toTopperModel().toLocal()
      topperRepository.insertNewTopperData(localTopper)
    }
  }


  fun loadAllDataFromSession() {
    scope.launch {

//      Timber.d(allData.size.toString())
    }
  }

  fun createNotificationHealth() {
    val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val att = AudioAttributes.Builder()
      .setUsage(AudioAttributes.USAGE_NOTIFICATION)
      .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
      .build()

    val notificationChannel = NotificationChannel(
      Constants.NOTIFICATION_CHANNEL,
      resources.getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH
    )
//    notificationChannel.importance = NotificationManager.IMPORTANCE_HIGH
    notificationChannel.enableLights(true)
    notificationChannel.enableVibration(true)
    notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC;
    notificationChannel.setShowBadge(false)
    notificationChannel.setSound(defaultSoundUri, att)


    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(notificationChannel)

    val restartIntent = Intent()
      .setClassName(this, Constants.INTENT_CLASS_MAIN_ACTIVITY)
      .setAction(Intent.ACTION_MAIN)
      .addCategory(Intent.CATEGORY_LAUNCHER)
    val restartPendingIntent =
      PendingIntent.getActivity(this, 1, restartIntent, PendingIntent.FLAG_IMMUTABLE)

    val builder = NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL)
//      .setSmallIcon(R.drawable.ic_bluetooth_searching)
      .setContentTitle(resources.getString(R.string.app_name))
      .setContentText(
        "장시간 앉아 계셨군요.\n" +
            "몸을 움직여줄 시간이에요!"
      )
      .setContentIntent(restartPendingIntent)
      .setSound(defaultSoundUri)
      .setCategory(NotificationCompat.CATEGORY_ALARM)

    val notification = builder.build()

    notificationManager.notify(1, notification)
  }

  private fun cancelNotification() {
    stopForeground(STOP_FOREGROUND_REMOVE)
  }

  override fun onSerialConnect() {
    if (connected) {
      synchronized(this) {
        if (bleDataListener != null) {
          mainLooper.post {
            if (bleDataListener != null) {
              bleDataListener!!.onSerialConnect()
            } else {
              queue1.add(QueueItem(QueueType.Connect))
            }
          }
        } else {
          queue2.add(QueueItem(QueueType.Connect))
        }
      }
    }
  }

  override fun onSerialConnectError(e: Exception) {
    if (connected) {
      synchronized(this) {
        if (bleDataListener != null) {
          mainLooper.post {
            if (bleDataListener != null) {
              bleDataListener!!.onSerialConnectError(e)
            } else {
              queue1.add(QueueItem(QueueType.ConnectError, e))
              disconnectBluetoothSocket()
            }
          }
        } else {
          queue2.add(QueueItem(QueueType.ConnectError, e))
          disconnectBluetoothSocket()
        }
      }
    }
  }

  override fun onSerialRead(datas: ArrayDeque<ByteArray>) {
    throw UnsupportedOperationException()
  }

  /**
   * reduce number of UI updates by merging data chunks.
   * Data can arrive at hundred chunks per second, but the UI can only
   * perform a dozen updates if receiveText already contains much text.
   *
   *
   * On new data inform UI thread once (1).
   * While not consumed (2), add more data (3).
   */
  override fun onSerialRead(data: ByteArray) {
    if (connected) {
      synchronized(this) {
        if (bleDataListener != null) {
          var first: Boolean
          synchronized(lastRead) {
            first = lastRead.datas!!.isEmpty() // (1)
            lastRead.add(data) // (3)
          }
          if (first) {
            mainLooper.post {
              var datas: ArrayDeque<ByteArray>
              synchronized(lastRead) {
                datas = lastRead.datas!!
                lastRead.init() // (2)
              }
              if (bleDataListener != null) {
                datas.let { bleDataListener!!.onSerialRead(it) }
              } else {
                queue1.add(QueueItem(QueueType.Read, datas))
              }
            }
          }
        } else {
          if (queue2.isEmpty() || queue2.last.type != QueueType.Read)
            queue2.add(QueueItem(QueueType.Read))
          queue2.last.add(data)
        }
      }
    }
  }

  override fun onSerialIoError(e: Exception) {
    if (connected) {
      synchronized(this) {
        if (bleDataListener != null) {
          mainLooper.post {
            if (bleDataListener != null) {
              bleDataListener!!.onSerialIoError(e)
            } else {
              queue1.add(QueueItem(QueueType.IoError, e))
              disconnectBluetoothSocket()
            }
          }
        } else {
          queue2.add(QueueItem(QueueType.IoError, e))
          disconnectBluetoothSocket()
        }
      }
    }
  }

  private fun createNotification() {
    val notificationChannel = NotificationChannel(
      Constants.NOTIFICATION_CHANNEL,
      "Background service", NotificationManager.IMPORTANCE_LOW
    )
    notificationChannel.setShowBadge(false)
    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(notificationChannel)
    val disconnectIntent = Intent().setAction(Constants.INTENT_ACTION_DISCONNECT)
    val restartIntent = Intent()
      .setClassName(this, Constants.INTENT_CLASS_MAIN_ACTIVITY)
      .setAction(Intent.ACTION_MAIN)
      .addCategory(Intent.CATEGORY_LAUNCHER)
    val flags = PendingIntent.FLAG_IMMUTABLE
    val disconnectPendingIntent = PendingIntent.getBroadcast(this, 1, disconnectIntent, flags)
    val restartPendingIntent = PendingIntent.getActivity(this, 1, restartIntent, flags)
    val builder = NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL)
//      .setSmallIcon(R.drawable.ic_bluetooth_searching)
      .setContentTitle(resources.getString(R.string.app_name))
      .setContentText(if (socket != null) "Connected to " + socket!!.name else "Background Service")
      .setContentIntent(restartPendingIntent)
      .setOngoing(true)
      .addAction(
        NotificationCompat.Action(
          R.drawable.ic_error,
          "Disconnect",
          disconnectPendingIntent
        )
      )
    // @drawable/ic_notification created with Android Studio -> New -> Image Asset using @color/colorPrimaryDark as background color
    // Android < API 21 does not support vectorDrawables in notifications, so both drawables used here, are created as .png instead of .xml
    val notification = builder.build()
    startForeground(Constants.NOTIFY_MANAGER_START_FOREGROUND_SERVICE, notification)
  }
}
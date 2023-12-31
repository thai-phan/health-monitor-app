package com.sewon.topperhealth.service.bluetooth

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.sewon.topperhealth.R
import com.sewon.topperhealth.data.irepository.ISessionRepository
import com.sewon.topperhealth.data.irepository.ITopperRepository
import com.sewon.topperhealth.data.model.toLocal
import com.sewon.topperhealth.service.algorithm.sleep.TopperData
import com.sewon.topperhealth.service.bluetooth.util.Constants
import com.sewon.topperhealth.service.bluetooth.util.QueueItem
import com.sewon.topperhealth.service.bluetooth.util.QueueType
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


@AndroidEntryPoint
class LowEnergyService : Service() {
  val tag = "TimberLowEnergyService"

  companion object {

    val isPlaySound = MutableLiveData(true)
    var sessionId = 0
    var pickerEndTime: Date = Date()
  }

//  val dataStore = HealthDataStore(this)
//  val referenceCount = dataStore.referenceCount.collect { value -> println(value) }

  private val job = SupervisorJob()
  private val scope = CoroutineScope(Dispatchers.IO + job)

  @Inject
  lateinit var topperRepository: ITopperRepository

  @Inject
  lateinit var sessionRepository: ISessionRepository

  inner class ServiceBinder : Binder() {
    val service: LowEnergyService
      get() = this@LowEnergyService
  }

  private val mainLooper: Handler = Handler(Looper.getMainLooper())
  private val binder: IBinder = ServiceBinder()
  private val lastRead: QueueItem = QueueItem(QueueType.Read)
  private var lowEnergyGatt: LowEnergyGatt? = null
  private var lowEnergyClient: LowEnergyClient? = null
  private var connected = false

  private lateinit var playerInduce: MediaPlayer
  private lateinit var playerDisconnect: MediaPlayer


  fun toggleSoundSleepInduce(value: Boolean) {
    isPlaySound.value = value
    if (this::playerInduce.isInitialized && LowEnergyClient.isStarted.value!!) {
      if (value) {
        playerInduce.start()
      } else {
        playerInduce.pause()
      }
    }
  }

  fun playSoundSleepInduce() {
    playerInduce = MediaPlayer.create(this, R.raw.sleep_induce_sound_2)
    if (isPlaySound.value == true) {
      playerInduce.start()
    }
  }

  fun stopSoundSleepInduce() {
    playerInduce.stop()
  }


  fun playSoundDisconnected() {
    playerDisconnect = MediaPlayer.create(this, R.raw.ring)
    playerDisconnect.start()
  }

  fun updateCurrentSessionRefValue(refHRV: Double, refHR: Double, refBR: Double) {
    scope.launch {
      sessionRepository.updateSessionRefValue(sessionId, refHRV, refHR, refBR)
    }
  }

  fun insertNewTopperToDatabase(topperData: TopperData) {
    scope.launch {
      val localTopper = topperData.toTopperModel().toLocal()
      topperRepository.insertNewTopperData(localTopper)
    }
  }

  override fun onDestroy() {
    cancelNotification()
    disconnectBluetoothGatt()
    super.onDestroy()
  }

  override fun onBind(intent: Intent): IBinder {
    return binder
  }

  @Throws(IOException::class)
  fun connect(gatt: LowEnergyGatt) {
    gatt.connect(this)
    this.lowEnergyGatt = gatt
    connected = true
  }

  fun disconnectBluetoothGatt() {
    connected = false // ignore data,errors while disconnecting
    cancelNotification()
    if (lowEnergyGatt != null) {
      lowEnergyGatt?.disconnect()
      lowEnergyGatt = null
    }
  }

  @Throws(IOException::class)
  fun writeFromService(data: ByteArray?) {
    if (!connected) throw IOException("not connected")
    if (data != null) {
      lowEnergyGatt!!.write(data)
    }
  }

  fun attach(listener: LowEnergyClient) {
    require(Looper.getMainLooper().thread === Thread.currentThread()) { "not in main thread" }
    cancelNotification()
    // use synchronized() to prevent new items in queue2
    // new items will not be added to queue1 because mainLooper.post and attach() run in main thread
    synchronized(this) { this.lowEnergyClient = listener }
  }


  fun onServiceConnect() {
    if (connected) {
      synchronized(this) {
        if (lowEnergyClient != null) {
          mainLooper.post {
            if (lowEnergyClient != null) {
              lowEnergyClient!!.onClientConnect()
            }
          }
        }
      }
    }
  }

  fun onServiceConnectError(e: Exception) {
    if (connected) {
      synchronized(this) {
        if (lowEnergyClient != null) {
          mainLooper.post {
            if (lowEnergyClient != null) {
              Timber.tag("onServiceConnectError").d("onServiceConnectError")
              lowEnergyClient!!.onClientConnectError(e)
            }
          }
        }
      }
    }
  }

  fun onServiceRead(datas: ArrayDeque<ByteArray>) {
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
  fun onServiceRead(data: ByteArray) {
    if (connected) {
      synchronized(this) {
        if (lowEnergyClient != null) {
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
              if (lowEnergyClient != null) {
                datas.let { lowEnergyClient!!.onClientReadLE(it) }
              }
            }
          }
        }
      }
    }
  }

  fun onServiceIoError(e: Exception) {
    if (connected) {
      synchronized(this) {
        if (lowEnergyClient != null) {
          mainLooper.post {
            if (lowEnergyClient != null) {
              Timber.tag("onServiceIoError").d("onServiceIoError")
              stopSoundSleepInduce()
              playSoundDisconnected()
              lowEnergyClient!!.onSerialIoError(e)
            }
          }
        }
      }
    }
  }

  fun createNotificationTopper() {
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
}
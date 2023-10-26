package com.sewon.topperhealth.service.bluetooth

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import com.sewon.topperhealth.service.bluetooth.util.ISerialListener
import com.sewon.topperhealth.service.bluetooth.util.QueueItem
import com.sewon.topperhealth.service.bluetooth.util.QueueType
import timber.log.Timber
import java.io.IOException
import java.util.ArrayDeque

/**
 * create notification and queue serial data while activity is not in the foreground
 * use listener chain: SerialSocket -> SerialService -> UI fragment
 */
class ClassicService : Service(), ISerialListener {
  internal inner class ClassicBinder : Binder() {
    val service: ClassicService
      get() = this@ClassicService
  }

  private val mainLooper: Handler = Handler(Looper.getMainLooper())
  private val lastRead: QueueItem = QueueItem(QueueType.Read)

  private val binder: IBinder = ClassicBinder()
  private var socket: ClassicGatt? = null
  private var listener: ISerialListener? = null
  var connected = false

  var deviceAddress = ""
  var deviceName = ""
//  val deviceUUID = mutableStateOf("")


  override fun onDestroy() {
    cancelNotification()
    disconnect()
    super.onDestroy()
  }

  override fun onBind(intent: Intent): IBinder {
    return binder
  }

  @Throws(IOException::class)
  fun connect(socket: ClassicGatt) {
    socket.connect(this)
    this.socket = socket
    connected = true
  }

  fun disconnect() {
    connected = false // ignore data,errors while disconnecting
    cancelNotification()
    if (socket != null) {
      socket!!.disconnect()
      socket = null
    }
  }

  @Throws(IOException::class)
  fun writeFromService(context: Context, data: ByteArray) {
    if (!connected) {
      Toast.makeText(context, "not connected", Toast.LENGTH_SHORT).show()
      return
    }
    socket?.writeFromGatt(data)
  }

  fun attach(listener: ISerialListener) {
    require(Looper.getMainLooper().thread === Thread.currentThread()) { "not in main thread" }
    cancelNotification()
    // use synchronized() to prevent new items in queue2
    // new items will not be added to queue1 because mainLooper.post and attach() run in main thread
    synchronized(this) {
      this.listener = listener
    }
  }

  fun detach() {
//    if (connected)
//      createNotification()
    // items already in event queue (posted before detach() to mainLooper) will end up in queue1
    // items occurring later, will be moved directly to queue2
    // detach() and mainLooper.post run in the main thread, so all items are caught
    listener = null
  }

  private fun cancelNotification() {
    stopForeground(true)
  }

  /**
   * SerialListener
   */
  override fun onSerialConnect() {
    if (connected) {
      synchronized(this) {
        mainLooper.post {
          listener?.onSerialConnect()
        }
      }
    }
  }


  override fun onSerialConnectError(e: Exception) {
    if (connected) {
      synchronized(this) {
        mainLooper.post {
          listener!!.onSerialConnectError(e)
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
        if (listener != null) {
          var first: Boolean
          synchronized(lastRead) {
            first = lastRead.datas!!.isEmpty() // (1)
            lastRead.add(data) // (3)
          }
          if (first) {
            mainLooper.post {
              var datas: ArrayDeque<ByteArray>?
              synchronized(lastRead) {
                datas = lastRead.datas
                lastRead.init() // (2)
              }
              datas?.let { listener?.onSerialRead(it) }

            }
          }
        }
      }
    }
  }

  override fun onSerialIoError(e: Exception) {
    if (connected) {
      synchronized(this) {
        mainLooper.post {
          Timber.tag("onSerialIoError").d("onSerialIoError")
          connected = false
          listener!!.onSerialIoError(e)
        }
      }
    }
  }
}
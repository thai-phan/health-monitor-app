package com.sewon.topperhealth.service.bluetooth

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.sewon.topperhealth.service.bluetooth.util.QueueItem
import com.sewon.topperhealth.service.bluetooth.util.QueueType
import timber.log.Timber
import java.io.IOException
import java.util.ArrayDeque

/**
 * create notification and queue serial data while activity is not in the foreground
 * use listener chain: SerialSocket -> SerialService -> UI fragment
 */
class ClassicService : Service() {

  companion object {
    //Define a LiveData to observe in activity
    val tokenLiveData = MutableLiveData<String>()
  }

  val TAG = this.javaClass.name

  internal inner class ServiceBinder : Binder() {
    val service: ClassicService
      get() = this@ClassicService
  }

  private val mainLooper: Handler = Handler(Looper.getMainLooper())
  private val lastRead: QueueItem = QueueItem(QueueType.Read)

  private val binder: IBinder = ServiceBinder()
  private var socket: ClassicGatt? = null
  private var client: ClassicClient? = null
  private var connected = false


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

  fun attach(listener: ClassicClient) {
    require(Looper.getMainLooper().thread === Thread.currentThread()) { "not in main thread" }
    cancelNotification()
    synchronized(this) {
      this.client = listener
    }
  }

  fun detach() {
//    if (connected)
//      createNotification()
    // items already in event queue (posted before detach() to mainLooper) will end up in queue1
    // items occurring later, will be moved directly to queue2
    // detach() and mainLooper.post run in the main thread, so all items are caught
    client = null
  }

  private fun cancelNotification() {
    stopForeground(STOP_FOREGROUND_REMOVE)
  }

  /**
   * SerialListener
   */
  fun onServiceConnect() {
    if (connected) {
      synchronized(this) {
        mainLooper.post {
          client?.onClientConnect()
        }
      }
    }
  }


  fun onServiceConnectError(e: Exception) {
    if (connected) {
      synchronized(this) {
        mainLooper.post {
          client!!.onSerialConnectError(e)
        }
      }
    }
  }

  fun onServiceRead(datas: ArrayDeque<ByteArray>) {
    throw UnsupportedOperationException()
  }


  fun onServiceRead(data: ByteArray) {
    if (connected) {
      synchronized(this) {
        if (client != null) {
          var first: Boolean
          synchronized(lastRead) {
            first = lastRead.datas!!.isEmpty()
            lastRead.add(data)
          }
          if (first) {
            mainLooper.post {
              var datas: ArrayDeque<ByteArray>?
              synchronized(lastRead) {
                datas = lastRead.datas
                lastRead.init()
              }
              datas?.let { client?.onClientRead(it) }
            }
          }
        }
      }
    }
  }

  fun onServiceIoError(e: Exception) {
    if (connected) {
      synchronized(this) {
        mainLooper.post {
          Timber.tag(TAG).d("onSerialIoError")
          connected = false
          client!!.onClientIoError(e)
        }
      }
    }
  }
}
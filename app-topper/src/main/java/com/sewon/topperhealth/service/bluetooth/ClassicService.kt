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
import java.io.IOException
import java.util.ArrayDeque


class ClassicService : Service() {
  val tag = "TimberClassicService"

  companion object {
    //Define a LiveData to observe in activity
    val tokenLiveData = MutableLiveData<String>()
  }


  internal inner class ServiceBinder : Binder() {
    val service: ClassicService
      get() = this@ClassicService
  }

  private val mainLooper: Handler = Handler(Looper.getMainLooper())
  private val lastRead: QueueItem = QueueItem(QueueType.Read)

  private val binder: IBinder = ServiceBinder()
  private var gatt: ClassicGatt? = null
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
    this.gatt = socket
    connected = true
  }

  fun disconnect() {
    connected = false // ignore data,errors while disconnecting
    cancelNotification()
    if (gatt != null) {
      gatt!!.disconnect()
      gatt = null
    }
  }

  @Throws(IOException::class)
  fun writeFromService(context: Context, data: ByteArray) {
    if (!connected) {
      Toast.makeText(context, "not connected", Toast.LENGTH_SHORT).show()
      return
    }
    gatt?.writeFromGatt(data)
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
          client!!.onClientConnectError(e)
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
          connected = false
          client!!.onClientIoError(e)
        }
      }
    }
  }
}
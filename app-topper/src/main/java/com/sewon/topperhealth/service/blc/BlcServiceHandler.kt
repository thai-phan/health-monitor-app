package com.sewon.topperhealth.service.blc

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import com.sewon.topperhealth.service.ISerialListener
import java.io.IOException
import java.util.ArrayDeque

/**
 * create notification and queue serial data while activity is not in the foreground
 * use listener chain: SerialSocket -> SerialService -> UI fragment
 */
class BlcServiceHandler : Service(), ISerialListener {
  internal inner class SerialBinder : Binder() {
    val service: BlcServiceHandler
      get() = this@BlcServiceHandler
  }

  private enum class QueueType {
    Connect, ConnectError, Read, IoError
  }

  private class QueueItem {
    var type: QueueType
    var datas: ArrayDeque<ByteArray>? = null
    var e: Exception? = null

    internal constructor(type: QueueType) {
      this.type = type
      if (type == QueueType.Read) init()
    }

    internal constructor(type: QueueType, e: Exception?) {
      this.type = type
      this.e = e
    }

    internal constructor(type: QueueType, datas: ArrayDeque<ByteArray>?) {
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
  private var socket: BlcGattSocket? = null
  private var listener: ISerialListener? = null
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
    disconnect()
    super.onDestroy()
  }

  override fun onBind(intent: Intent): IBinder {
    return binder
  }


  @Throws(IOException::class)
  fun connect(socket: BlcGattSocket) {
//    TODO: aaa
//    socket.connect()
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
  fun write(data: ByteArray?) {
    if (!connected) throw IOException("not connected")
    socket?.write(data)
  }

  fun attach(listener: ISerialListener) {
    require(Looper.getMainLooper().thread === Thread.currentThread()) { "not in main thread" }
    cancelNotification()
    // use synchronized() to prevent new items in queue2
    // new items will not be added to queue1 because mainLooper.post and attach() run in main thread
    synchronized(this) { this.listener = listener }
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
        if (listener != null) {
          mainLooper.post {
            listener?.onSerialConnect() ?: queue1.add(QueueItem(QueueType.Connect))
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
        if (listener != null) {
          mainLooper.post {
            if (listener != null) {
              listener!!.onSerialConnectError(e)
            } else {
              queue1.add(QueueItem(QueueType.ConnectError, e))
              disconnect()
            }
          }
        } else {
          queue2.add(QueueItem(QueueType.ConnectError, e))
          disconnect()
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
                ?: queue1.add(QueueItem(QueueType.Read, datas))
            }
          }
        } else {
          if (queue2.isEmpty() || queue2.last.type != QueueType.Read) queue2.add(
            QueueItem(QueueType.Read)
          )
          queue2.last.add(data)
        }
      }
    }
  }

  override fun onSerialIoError(e: Exception) {
    if (connected) {
      synchronized(this) {
        if (listener != null) {
          mainLooper.post {
            if (listener != null) {
              listener!!.onSerialIoError(e)
            } else {
              queue1.add(QueueItem(QueueType.IoError, e))
              disconnect()
            }
          }
        } else {
          queue2.add(QueueItem(QueueType.IoError, e))
          disconnect()
        }
      }
    }
  }
}
package com.sewon.topperhealth.service.blc

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.sewon.topperhealth.service.ISerialListener
import com.sewon.topperhealth.service.ble.Constants.INTENT_ACTION_DISCONNECT
import java.io.IOException
import java.security.InvalidParameterException
import java.util.Arrays
import java.util.UUID
import java.util.concurrent.Executors

@SuppressLint("MissingPermission")
class BlcGattSocket(context: Context, device: BluetoothDevice) : Runnable {
  private val disconnectBroadcastReceiver: BroadcastReceiver
  private val context: Context
  private var listener: ISerialListener? = null
  private val device: BluetoothDevice
  private var socket: BluetoothSocket? = null
  private var connected = false

  init {
//    if (context is Activity) throw InvalidParameterException("expected non UI context")
    this.context = context
    this.device = device
    disconnectBroadcastReceiver = object : BroadcastReceiver() {
      override fun onReceive(context: Context, intent: Intent) {
        listener?.onSerialIoError(IOException("background disconnect"))
        disconnect() // disconnect now, else would be queued until UI re-attached
      }
    }
  }

  val name: String
    get() = if (device.name != null) device.name else device.address

  /**
   * connect-success and most connect-errors are returned asynchronously to listener
   */
  @SuppressLint("UnspecifiedRegisterReceiverFlag")
  @Throws(IOException::class)
  fun connect(listener: ISerialListener) {
    this.listener = listener
    context.registerReceiver(
      disconnectBroadcastReceiver,
      IntentFilter(INTENT_ACTION_DISCONNECT)
    )
    Executors.newSingleThreadExecutor().submit(this)
  }

  fun disconnect() {
    listener = null // ignore remaining data and errors
    // connected = false; // run loop will reset connected
    if (socket != null) {
      try {
        socket!!.close()
      } catch (ignored: Exception) {
      }
      socket = null
    }
    try {
      context.unregisterReceiver(disconnectBroadcastReceiver)
    } catch (ignored: Exception) {
    }
  }

  @Throws(IOException::class)
  fun write(data: ByteArray?) {
    if (!connected) throw IOException("not connected")
    socket!!.outputStream.write(data)
  }

  override fun run() { // connect & read
    try {
      socket = device.createRfcommSocketToServiceRecord(BLUETOOTH_SPP)
      socket?.connect()
      listener?.onSerialConnect()
    } catch (e: Exception) {
      listener?.onSerialConnectError(e)
      try {
        socket!!.close()
      } catch (ignored: Exception) {
      }
      socket = null
      return
    }
    connected = true
    try {
      val buffer = ByteArray(1024)
      var len: Int
      while (true) {
        len = socket!!.getInputStream().read(buffer)
        val data = Arrays.copyOf(buffer, len)
        listener?.onSerialRead(data)
      }
    } catch (e: Exception) {
      connected = false
      listener?.onSerialIoError(e)
      try {
        socket?.close()
      } catch (ignored: Exception) {
      }
      socket = null
    }
  }

  companion object {
    private val BLUETOOTH_SPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
  }
}
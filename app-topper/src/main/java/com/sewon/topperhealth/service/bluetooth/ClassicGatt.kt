package com.sewon.topperhealth.service.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.sewon.topperhealth.service.bluetooth.util.Constants.INTENT_ACTION_DISCONNECT
import timber.log.Timber
import java.io.IOException
import java.util.Arrays
import java.util.UUID
import java.util.concurrent.Executors

@SuppressLint("MissingPermission")
class ClassicGatt(context: Context, device: BluetoothDevice) : Runnable {
  private val disconnectBroadcastReceiver: BroadcastReceiver
  private val context: Context
  private var service: ClassicService? = null
  private val device: BluetoothDevice
  private var bluetoothSocket: BluetoothSocket? = null
  private var connected = false

  init {
//    if (context is Activity) throw InvalidParameterException("expected non UI context")
    this.context = context
    this.device = device
    disconnectBroadcastReceiver = object : BroadcastReceiver() {
      override fun onReceive(context: Context, intent: Intent) {
        service?.onServiceIoError(IOException("background disconnect"))
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
  fun connect(service: ClassicService) {
    this.service = service
    context.registerReceiver(
      disconnectBroadcastReceiver,
      IntentFilter(INTENT_ACTION_DISCONNECT)
    )
    Executors.newSingleThreadExecutor().submit(this)
  }

  fun disconnect() {
    service = null // ignore remaining data and errors
    // connected = false; // run loop will reset connected
    if (bluetoothSocket != null) {
      try {
        bluetoothSocket!!.close()
      } catch (ignored: Exception) {
      }
      bluetoothSocket = null
    }
    try {
      context.unregisterReceiver(disconnectBroadcastReceiver)
    } catch (ignored: Exception) {
    }
  }

  @Throws(IOException::class)
  fun writeFromGatt(data: ByteArray?) {
    if (!connected) throw IOException("not connected")
    bluetoothSocket!!.outputStream.write(data)
  }

  override fun run() { // connect & read
    try {
      bluetoothSocket = device.createRfcommSocketToServiceRecord(BLUETOOTH_SPP)
      bluetoothSocket?.connect()
      service?.onServiceConnect()
    } catch (e: Exception) {
      Timber.tag("ClassicGatt").d("bluetoothSocket Exception")
      service?.onServiceConnectError(e)
      try {
        bluetoothSocket!!.close()
      } catch (ignored: Exception) {
      }
      bluetoothSocket = null
      return
    }
    connected = true
    try {
      val buffer = ByteArray(1024)
      var len: Int
      while (true) {
        len = bluetoothSocket!!.getInputStream().read(buffer)
        val data = Arrays.copyOf(buffer, len)
        service?.onServiceRead(data)
      }
    } catch (e: Exception) {
      connected = false
      service?.onServiceIoError(e)
      try {
        bluetoothSocket?.close()
      } catch (ignored: Exception) {
      }
      bluetoothSocket = null
    }
  }

  companion object {
    private val BLUETOOTH_SPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
  }
}
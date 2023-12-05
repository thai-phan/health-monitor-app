package com.sewon.topperhealth.service.bluetooth


import androidx.lifecycle.MutableLiveData
import com.sewon.topperhealth.service.bluetooth.util.Connected
import com.sewon.topperhealth.service.bluetooth.util.Constants.BLUETOOTH_DISCONNECTED
import com.sewon.topperhealth.service.bluetooth.util.Constants.BLUETOOTH_NO_CONNECTION
import java.util.ArrayDeque


class ClassicClient {
  val tag = "TimberClassicClient"

  companion object {
    var connected = MutableLiveData(Connected.NotConnected)
    var deviceAddress = MutableLiveData("")
    var deviceName = MutableLiveData(BLUETOOTH_NO_CONNECTION)
  }


  fun onClientConnect() {
    connected.value = Connected.True
  }

  fun onClientConnectError(e: Exception) {
    disconnect()
  }

  private fun disconnect() {
    connected.value = Connected.False
    deviceAddress.value = "Disconnected"
    deviceName.value = BLUETOOTH_DISCONNECTED
  }

  fun onClientRead(data: ByteArray) {
    val datas = ArrayDeque<ByteArray>()
    datas.add(data)
    receive(datas)
  }

  fun onClientRead(datas: ArrayDeque<ByteArray>) {
    receive(datas)
  }

  fun onClientIoError(e: Exception) {
    disconnect()
  }

  private fun receive(datas: ArrayDeque<ByteArray>) {

  }
}
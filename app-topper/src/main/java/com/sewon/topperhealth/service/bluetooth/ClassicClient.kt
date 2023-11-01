package com.sewon.topperhealth.service.bluetooth

import androidx.lifecycle.MutableLiveData
import com.sewon.topperhealth.service.bluetooth.util.Connected
import com.sewon.topperhealth.service.bluetooth.util.Constants.BLUETOOTH_DISCONNECTED
import com.sewon.topperhealth.service.bluetooth.util.Constants.BLUETOOTH_NO_CONNECTION


import timber.log.Timber
import java.util.ArrayDeque


class ClassicClient {
  companion object {
    var connected = MutableLiveData(Connected.False)
    var deviceAddress = MutableLiveData("")
    var deviceName = MutableLiveData(BLUETOOTH_NO_CONNECTION)
  }

  val tag: String = this.javaClass.name


  fun onClientConnect() {
    connected.value = Connected.True
  }

  fun onClientConnectError(e: Exception) {
    disconnect()
  }

  private fun disconnect() {
    connected.value = Connected.False
    deviceAddress.value = ""
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
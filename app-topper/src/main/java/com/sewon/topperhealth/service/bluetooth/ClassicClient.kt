package com.sewon.topperhealth.service.bluetooth

import android.text.SpannableStringBuilder
import androidx.compose.runtime.mutableStateOf
import com.sewon.topperhealth.service.algorithm.sleep.realtime.RealtimeDataProcessing
import com.sewon.topperhealth.service.bluetooth.util.Connected
import com.sewon.topperhealth.service.bluetooth.util.TextUtil


import timber.log.Timber
import java.util.ArrayDeque


class ClassicClient {

  val TAG = this.javaClass.name

  var connected = mutableStateOf(Connected.False)
  var deviceAddress = mutableStateOf("")
  var deviceName = mutableStateOf("")

  var classicLog = ""

  var isAlarm = false


  fun startAlarmListener() {
    isAlarm = true
  }


  fun onClientConnect() {
    Timber.tag("ClassicClient").d("Connected.True")
    connected.value = Connected.True
  }

  fun onSerialConnectError(e: Exception) {
    disconnect()
  }

  private fun disconnect() {
    connected.value = Connected.False
    deviceAddress.value = ""
    deviceName.value = ""
//    service.disconnect()
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
    Timber.tag(TAG).d("onClientIoError")
    disconnect()

  }

  private var sensorLoopCount = 0

  private fun receive(datas: ArrayDeque<ByteArray>) {
    val stringBuilder = SpannableStringBuilder()
    for (data in datas) {
      val dataStr = String(data)
      RealtimeDataProcessing.validateDataFormat(dataStr)
      val text = TextUtil.toCaretString(dataStr, true)
      stringBuilder.append(text)
    }

    if (sensorLoopCount < 2) {
      sensorLoopCount += 1
    } else {
      sensorLoopCount = 0
    }
    classicLog = "$stringBuilder $sensorLoopCount"
  }
}
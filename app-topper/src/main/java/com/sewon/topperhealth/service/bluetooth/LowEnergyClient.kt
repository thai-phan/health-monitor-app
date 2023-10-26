package com.sewon.topperhealth.service.bluetooth

import android.text.SpannableStringBuilder
import androidx.compose.runtime.mutableStateOf
import com.sewon.topperhealth.service.alarm.AlarmReceiver
import com.sewon.topperhealth.service.algorithm.sleep.realtime.RealtimeDataProcessing
import com.sewon.topperhealth.service.bluetooth.util.Connected
import com.sewon.topperhealth.service.bluetooth.util.TextUtil
import timber.log.Timber
import java.util.ArrayDeque


class LowEnergyClient {
  val TAG = this.javaClass.name

  private var connected = Connected.False
  private val hexEnabled = false

  var log = mutableStateOf("")

  var isAlarm = mutableStateOf(false)


  fun startAlarmListener() {
    isAlarm.value = true
  }

  // TODO:
  fun stopAlarmListener() {
    isAlarm.value = false
    AlarmReceiver.ringtone.stop()
    AlarmReceiver.vibrator.cancel()
  }


  fun onClientConnect() {
    connected = Connected.True
  }

  fun onClientConnectError(e: Exception) {
    connected = Connected.False
  }

  fun onClientRead(data: ByteArray) {
    val datas = ArrayDeque<ByteArray>()
    datas.add(data)
    receive(datas)
  }

  fun onClientRead(datas: ArrayDeque<ByteArray>) {
    receive(datas)
  }

  fun onSerialIoError(e: Exception) {
    Timber.tag(TAG).d("onSerialRead")
  }

  private var sensorLoopCount = 0

  private fun receive(datas: ArrayDeque<ByteArray>) {
    val stringBuilder = SpannableStringBuilder()
    for (data in datas) {
      if (hexEnabled) {
        stringBuilder.append(TextUtil.toHexString(data)).append('\n')
      } else {
        val dataStr = String(data)
        RealtimeDataProcessing.validateDataFormat(dataStr)
        val text = TextUtil.toCaretString(dataStr, true)
        stringBuilder.append(text)
      }
    }

    if (sensorLoopCount < 2) {
      sensorLoopCount += 1
    } else {
      sensorLoopCount = 0
    }
    log.value = "$stringBuilder $sensorLoopCount"
  }
}
package com.sewon.topperhealth.service.bluetooth

import android.text.SpannableStringBuilder
import androidx.lifecycle.MutableLiveData
import com.sewon.topperhealth.service.alarm.AlarmReceiver
import com.sewon.topperhealth.service.algorithm.sleep.realtime.RealtimeHandler
import com.sewon.topperhealth.service.bluetooth.util.Connected
import com.sewon.topperhealth.service.bluetooth.util.TextUtil
import timber.log.Timber
import java.util.ArrayDeque


class LowEnergyClient {
  val tag: String = "TimberLowEnergyClient"

  companion object {
    val deviceAddress = MutableLiveData("")
    val deviceName = MutableLiveData("Not Connected")
    val log = MutableLiveData("")
    val isAlarm = MutableLiveData(false)
    val connected = MutableLiveData(Connected.NotConnected)
    val isStarted = MutableLiveData(false)
  }


  private val hexEnabled = false

  fun startAlarmListener() {
    isAlarm.value = true
  }

  fun stopAlarmListener() {
    isAlarm.value = false
    AlarmReceiver.ringtone.stop()
    AlarmReceiver.vibrator.cancel()
  }

  fun onClientConnect() {
    connected.value = Connected.True
  }

  fun onClientConnectError(e: Exception) {
    connected.value = Connected.False
    Timber.tag(tag).d(e)
  }

  fun onClientReadLE(data: ByteArray) {
    val datas = ArrayDeque<ByteArray>()
    datas.add(data)
    onClientReceiveLE(datas)
  }

  fun onClientReadLE(datas: ArrayDeque<ByteArray>) {
    onClientReceiveLE(datas)
  }

  fun onSerialIoError(e: Exception) {
    connected.value = Connected.False
    Timber.tag(tag).d(e)
  }

  private var sensorLoopCount = 0

  private fun onClientReceiveLE(datas: ArrayDeque<ByteArray>) {
    val stringBuilder = SpannableStringBuilder()
    for (data in datas) {
      if (hexEnabled) {
        stringBuilder.append(TextUtil.toHexString(data)).append('\n')
      } else {
        val dataStr = String(data)
        RealtimeHandler.validateDataFormat(dataStr)
        val text = TextUtil.toCaretString(dataStr, true)
        stringBuilder.append(text)
      }
    }

    if (sensorLoopCount < 5) {
      sensorLoopCount += 1
    } else {
      sensorLoopCount = 0
    }
    log.value = "$stringBuilder $sensorLoopCount"
  }
}
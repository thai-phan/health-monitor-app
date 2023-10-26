package com.sewon.topperhealth.service.bluetooth

import android.text.SpannableStringBuilder
import com.sewon.topperhealth.service.alarm.AlarmReceiver
import com.sewon.topperhealth.service.algorithm.sleep.realtime.RealtimeDataProcessing
import com.sewon.topperhealth.service.bluetooth.util.ISerialListener
import com.sewon.topperhealth.service.bluetooth.util.TextUtil


import timber.log.Timber
import java.util.ArrayDeque


class LowEnergyListener : ISerialListener {
  private enum class Connected {
    False, Pending, True
  }

  private var connected = Connected.False
  private val hexEnabled = false

  var log = ""

  var isAlarm = false


  fun startAlarmListener() {
    isAlarm = true
  }

  // TODO:
  fun stopAlarmListener() {
    isAlarm = false
    AlarmReceiver.ringtone.stop()
    AlarmReceiver.vibrator.cancel()
  }


  override fun onSerialConnect() {
    connected = Connected.True
  }

  override fun onSerialConnectError(e: Exception) {
    connected = Connected.False
  }

  override fun onSerialRead(data: ByteArray) {
    val datas = ArrayDeque<ByteArray>()
    datas.add(data)
    receive(datas)
  }

  override fun onSerialRead(datas: ArrayDeque<ByteArray>) {
    receive(datas)
  }

  override fun onSerialIoError(e: Exception) {
    Timber.tag("Timber").d("onSerialRead")
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
    log = "$stringBuilder $sensorLoopCount"
  }
}
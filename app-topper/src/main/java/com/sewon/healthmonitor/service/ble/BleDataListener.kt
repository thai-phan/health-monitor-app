package com.sewon.healthmonitor.service.ble

import android.os.CountDownTimer
import android.text.SpannableStringBuilder
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import com.sewon.healthmonitor.MainActivity
import com.sewon.healthmonitor.service.alarm.AlarmReceiver
import com.sewon.healthmonitor.service.algorithm.sleep.realtime.RealtimeDataProcessing


import timber.log.Timber
import java.util.ArrayDeque


class BleDataListener : SerialListener {


  private enum class Connected {
    False, Pending, True
  }

  private var connected = Connected.False
  private val hexEnabled = false

  val log = mutableStateOf("")

  val isAlarm = mutableStateOf(false)

  private val dataArrayList = ArrayList<Double>()


  private val b = 1000L
  val timeRemaining = mutableLongStateOf(0)


  fun startAlarmListener() {
    isAlarm.value = true
  }

  fun stopAlarmListener() {
    isAlarm.value = false
    if (AlarmReceiver.ringtone != null) {
      AlarmReceiver.ringtone.stop()
    }
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
        RealtimeDataProcessing.validateDataFormatAndProcess(dataStr)
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
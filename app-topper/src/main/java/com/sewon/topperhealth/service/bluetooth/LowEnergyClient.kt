package com.sewon.topperhealth.service.bluetooth

import android.text.SpannableStringBuilder
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import com.sewon.topperhealth.MainActivity
import com.sewon.topperhealth.service.alarm.AlarmReceiver
import com.sewon.topperhealth.service.algorithm.sleep.realtime.RealtimeHandler
import com.sewon.topperhealth.service.bluetooth.util.Connected
import com.sewon.topperhealth.service.bluetooth.util.TextUtil
import timber.log.Timber
import java.util.ArrayDeque


class LowEnergyClient {
  val tag = "TimberLowEnergyClient"

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
    connected.value = Connected.Pending
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
    connected.value = Connected.True
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
        validateDataFormat(dataStr)
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

  private val isWrongDeviceType = mutableStateOf(false)

  private val regex = Regex("[01234]")

  private fun validateDataFormat(dataStr: String) {
    val messageList = dataStr.split(" ").filter { it != "" }
    if (messageList[0].matches(regex)) {
      RealtimeHandler.receiveData(messageList)
    } else {
      isWrongDeviceType.value = true
      MainActivity.lowEnergyService.disconnectBluetoothGatt()
    }
  }
}
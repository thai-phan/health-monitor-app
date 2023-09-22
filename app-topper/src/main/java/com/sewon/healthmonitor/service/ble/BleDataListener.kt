package com.sewon.healthmonitor.service.ble

import android.os.CountDownTimer
import android.text.SpannableStringBuilder
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import com.sewon.healthmonitor.MainActivity
import com.sewon.healthmonitor.algorithm.sleep.SleepAlgorithm


import timber.log.Timber
import java.util.ArrayDeque


class BleDataListener : SerialListener {


  private enum class Connected {
    False, Pending, True
  }

  private var connected = Connected.False
  private val hexEnabled = false
  val receiveText: SpannableStringBuilder = SpannableStringBuilder()

  val log = mutableStateOf("")
  val isStretch = mutableStateOf(false)
  val isStress = mutableStateOf(false)


  private val dataArrayList = ArrayList<Double>()

  private val totalDuration = 50 * 60 * 1000L
//  private val totalDuration = 10 * 1000L

  private val b = 1000L
  val timeRemaining = mutableLongStateOf(0)


  val countDownTimer = object : CountDownTimer(totalDuration, b) {
    override fun onTick(millisUntilFinished: Long) {
      timeRemaining.longValue = totalDuration - millisUntilFinished
    }

    override fun onFinish() {
      stretchDetected()
      timeRemaining.longValue = 0
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

  fun resetTimer() {
    countDownTimer.cancel()
    countDownTimer.start()
  }


  fun stretchDetected() {
    MainActivity.bleHandleService.createNotificationHealth()
    isStretch.value = true
  }

  fun stretchStop() {
    isStretch.value = false
  }

  fun stressDetected() {
    MainActivity.bleHandleService.createNotificationHealth()
    isStress.value = true
  }

  fun stressStop() {
    isStress.value = false
  }

//  var prevValue = Constants.STABLE_MOVING;

  private var countNoVitalSign = 0
  private var countNoTarget = 0

  private var updateDBCount = 0

  private fun processData(messageList: List<String>) {


    SleepAlgorithm.inputData(messageList)


    MainActivity.bleHandleService.updateDatabase(messageList)

    if (updateDBCount == 5) {
      updateDBCount = 0
    }
    updateDBCount += 1

    //  STABLE_NO_VITAL_SIGN = "1"
    if (messageList[0] == Constants.STABLE_NO_VITAL_SIGN) {
      countNoVitalSign += 1
      if (countNoVitalSign == Constants.NO_VITAL_SIGN_THRESHOLD) {
        resetTimer()
        countNoVitalSign = 0
      }
    } else {
      countNoVitalSign = 0
    }

    //  STABLE_NO_TARGET = "0"
    if (messageList[0] == Constants.STABLE_NO_TARGET) {
      countNoTarget += 1
      if (countNoTarget == Constants.NO_TARGET_THRESHOLD) {
        resetTimer()
        countNoTarget = 0
      }
    } else {
      countNoTarget = 0
    }


//    prevValue = messageArray[0]

    if (dataArrayList.size == 9) {
//      val result = ECG_ANALYSIS_PROC.ECG_AnalysisData(dataArrayList)
//      if (result.get(0).stress_State == "stress") {
//        stressDetected()
//      }
      dataArrayList.clear()
    }
    dataArrayList.add(messageList.get(3).toDouble())
  }

  val isWrongDeviceType = mutableStateOf(false)

  private fun validateDataFormatAndProcess(dataStr: String) {
    val messageList = dataStr.split(" ").filter { it != "" }
    if (messageList.size == 6 && messageList[0].matches(regex)) {
      processData(messageList)
    } else {
      isWrongDeviceType.value = true
      MainActivity.bleHandleService.disconnect()
    }
  }

  private var tempCount = 0

  private fun receive(datas: ArrayDeque<ByteArray>) {
    val stringBuilder = SpannableStringBuilder()
    for (data in datas) {
      if (hexEnabled) {
        stringBuilder.append(TextUtil.toHexString(data)).append('\n')
      } else {
        val message = String(data)
        processData(message)
        val text = TextUtil.toCaretString(message, true)
        stringBuilder.append(text)
      }
    }

    if (tempCount < 2) {
      tempCount += 1
    } else {
      tempCount = 0
    }

    log.value = "$stringBuilder $tempCount"
  }


}
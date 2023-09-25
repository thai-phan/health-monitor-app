package com.sewon.healthmonitor.service.algorithm.sleep

import androidx.compose.runtime.mutableStateOf
import com.sewon.healthmonitor.MainActivity

class DataProcessing {

  companion object {
    val isWrongDeviceType = mutableStateOf(false)

    private val regex = Regex("[01234]")

    fun validateDataFormatAndProcess(dataStr: String) {
      val messageList = dataStr.split(" ").filter { it != "" }
      if (messageList.size == 6 && messageList[0].matches(regex)) {
        processData(messageList)
      } else {
        isWrongDeviceType.value = true
        MainActivity.bleHandleService.disconnect()
      }
    }

    private var deplayCount = 0

    fun processData(messageList: List<String>) {
      val sensorData = SensorData(messageList)

//      AlgorithmRealtime.inputData(sensorData)

      if (deplayCount == 5) {
        MainActivity.bleHandleService.updateDatabase(sensorData)
        deplayCount = 0
      }
      deplayCount += 1
    }
  }
}
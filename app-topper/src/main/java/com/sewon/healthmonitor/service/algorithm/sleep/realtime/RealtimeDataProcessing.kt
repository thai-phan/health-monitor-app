package com.sewon.healthmonitor.service.algorithm.sleep.realtime

import androidx.compose.runtime.mutableStateOf
import com.sewon.healthmonitor.MainActivity
import com.sewon.healthmonitor.service.algorithm.sleep.TopperData

class RealtimeDataProcessing {

  companion object {
    private val isWrongDeviceType = mutableStateOf(false)

    private val regex = Regex("[01234]")

    fun validateDataFormatAndProcess(dataStr: String) {
      val messageList = dataStr.split(" ").filter { it != "" }
      if (messageList.size == 6 && messageList[0].matches(regex)) {
        processData(messageList)
      } else {
        isWrongDeviceType.value = true
        MainActivity.bleHandleService.disconnectBluetoothSocket()
      }
    }

    private var deplayCount = 0

    fun processData(messageList: List<String>) {
      val topperData = TopperData(MainActivity.bleHandleService.sessionId, messageList)

//      RealtimeAlgorithm.inputData(topperData)

      if (deplayCount == 1) {
        MainActivity.bleHandleService.insertNewTopperToDatabase(topperData)
        deplayCount = 0
      }
      deplayCount += 1
    }
  }
}
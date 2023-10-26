package com.sewon.topperhealth.service.algorithm.sleep.realtime

import androidx.compose.runtime.mutableStateOf
import com.sewon.topperhealth.MainActivity
import com.sewon.topperhealth.service.algorithm.sleep.TopperData

class RealtimeDataProcessing {

  companion object {
    private val isWrongDeviceType = mutableStateOf(false)

    private val regex = Regex("[01234]")

    fun validateDataFormat(dataStr: String) {
      val messageList = dataStr.split(" ").filter { it != "" }
      if (messageList.size == 6 && messageList[0].matches(regex)) {
        processData(messageList)
      } else {
        isWrongDeviceType.value = true
        MainActivity.lowEnergyService.disconnectBluetoothSocket()
      }
    }

    private var deplayCount = 0

    fun processData(messageList: List<String>) {
      val topperData = TopperData(MainActivity.lowEnergyService.sessionId, messageList)

//      RealtimeAlgorithm.processData(topperData)

      MainActivity.lowEnergyService.insertNewTopperToDatabase(topperData)

//      if (deplayCount == 1) {
//        deplayCount = 0
//      }
//      deplayCount += 1
    }
  }
}
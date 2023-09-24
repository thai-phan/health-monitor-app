package com.sewon.healthmonitor.algorithm.sleep

import com.sewon.healthmonitor.MainActivity

class AlgorithmRealtime {

  companion object {
    var firstReferenceCount = 3 * 60 * 20
    var sumHRV = 0.0
    var sumHR = 0.0
    var sumBR = 0.0
    var firstMeanHRV = 0.0
    var firstMeanHR = 0.0
    var firstMeanBR = 0.0
    var countReferenceData = 0

    var lux = 10
    var curlux = 10


    fun inputData(dataList: List<String>) {
      val sensorData = SensorData(dataList)

      countReferenceData += 1

      if (countReferenceData < firstReferenceCount) {
        sumHRV += sensorData.HRV
        sumHR += sensorData.HR
        sumBR += sensorData.BR
      }

      if (countReferenceData == firstReferenceCount) {
        firstMeanHRV = sumHRV / firstReferenceCount
        firstMeanHR = sumHR / firstReferenceCount
        firstMeanBR = sumBR / firstReferenceCount
      }

      if (curlux < lux) {


        if (sensorData.HRV > firstMeanHRV * Constants.HRV_THRESHOLD) {
          if (sensorData.HR < firstMeanHR * Constants.HR_THRESHOLD) {
            if (sensorData.BR < firstMeanBR * Constants.BR_THRESHOLD) {
              sleepStart(sensorData)
            }
          }
        }
      }
    }


    var countInsomniaValue = 0
    var countDeepSleep = 0

    fun sleepStart(sensorData: SensorData) {
      if (sensorData.stable == 0 || sensorData.stable == 1) {
        if (countInsomniaValue == 1200) {
          callInsomnia(sensorData)
        }
        countInsomniaValue += 1
      } else {
        countInsomniaValue = 0
      }

      if (sensorData.stable == 2) {
        if (countDeepSleep == 1200) {
          callDeepSleep(sensorData)
        }
        countDeepSleep += 1
      } else {
        countDeepSleep = 0
      }
    }

    fun callInsomnia(sensorData: SensorData) {

      if (sensorData.HR != 0 && sensorData.BR != 0) {
        if (sensorData.HR - firstMeanHR > 5 || firstMeanHR - sensorData.HR > 5) {
          saveDatabase(sensorData)
        }
      } else {
        sensorData.isSleep = false
        saveDatabase(sensorData)
      }
    }

    fun callDeepSleep(sensorData: SensorData) {
      var curTime = 0
      var sleepTime = 0
      var isWakeup = true
      if (curTime < sleepTime) {
        saveDatabase(sensorData)
      } else {
        callAlarm()
        if (sensorData.HR != 0 && sensorData.BR != 0) {
          if (sensorData.HR - firstMeanHR > 5 || firstMeanHR - sensorData.HR > 5) {
            saveDatabase(sensorData)
          }
        } else {
          sensorData.isSleep = false
          saveDatabase(sensorData)
        }
      }
    }

    fun callAlarm() {

    }

    fun saveDatabase(sensorData: SensorData) {
      MainActivity.bleHandleService.updateDatabase(sensorData)
    }


    // Sleep ble data

    // First 3 min data

    // Mean HRV, HR, BR

    // Lux check < 10

    //
  }
}
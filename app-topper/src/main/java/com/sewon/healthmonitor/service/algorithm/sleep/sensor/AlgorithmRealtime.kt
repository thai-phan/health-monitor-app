package com.sewon.healthmonitor.service.algorithm.sleep.sensor

import com.sewon.healthmonitor.service.algorithm.sleep.AlgorithmConstants
import com.sewon.healthmonitor.service.algorithm.sleep.SensorData


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

    var lux = 1
    var curlux = 10


    fun inputData(sensorData: SensorData) {

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
        if (sensorData.HRV > firstMeanHRV * AlgorithmConstants.REALTIME_HRV_THRESHOLD) {
          if (sensorData.HR < firstMeanHR * AlgorithmConstants.REALTIME_HR_THRESHOLD) {
            if (sensorData.BR < firstMeanBR * AlgorithmConstants.REALTIME_BR_THRESHOLD) {
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
      var sleepTime = 1
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
//      MainActivity.bleHandleService.updateDatabase(sensorData)
    }
  }
}
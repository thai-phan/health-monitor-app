package com.sewon.healthmonitor.service.algorithm.sleep.realtime

import com.sewon.healthmonitor.MainActivity
import com.sewon.healthmonitor.service.algorithm.sleep.AlgorithmConstants
import com.sewon.healthmonitor.service.algorithm.sleep.TopperData
import timber.log.Timber


class RealtimeAlgorithm {

  companion object {
    var firstReferenceCount = 3 * 60 * 20
    var sumHRV = 0.0
    var sumHR = 0.0
    var sumBR = 0.0
    var refHRV = 0.0
    var refHR = 0.0
    var refBR = 0.0
    var countReferenceData = 0

    var lux = 1
    var curlux = 10


    fun inputData(topperData: TopperData) {
      countReferenceData += 1
      if (countReferenceData < firstReferenceCount) {
        sumHRV += topperData.HRV
        sumHR += topperData.HR
        sumBR += topperData.BR
      }

      if (countReferenceData == firstReferenceCount) {
        refHRV = sumHRV / firstReferenceCount
        refHR = sumHR / firstReferenceCount
        refBR = sumBR / firstReferenceCount

        MainActivity.bleHandleService.updateCurrentSessionRefValue(refHRV, refHR, refBR)
      }

//      if (curlux < lux) {
      if (topperData.HRV > refHRV * AlgorithmConstants.REALTIME_HRV_THRESHOLD) {
        if (topperData.HR < refHR * AlgorithmConstants.REALTIME_HR_THRESHOLD) {
          if (topperData.BR < refBR * AlgorithmConstants.REALTIME_BR_THRESHOLD) {
            sleepStart(topperData)
          }
        }
      }
//      }
    }


    var countInsomniaValue = 0
    var countDeepSleep = 0

    fun sleepStart(topperData: TopperData) {
      if (topperData.stable == 0 || topperData.stable == 1) {
        if (countInsomniaValue == 1200) {
          callInsomnia(topperData)
        }
        countInsomniaValue += 1
      } else {
        countInsomniaValue = 0
      }

      if (topperData.stable == 2) {
        if (countDeepSleep == 1200) {
          callDeepSleep(topperData)
        }
        countDeepSleep += 1
      } else {
        countDeepSleep = 0
      }
    }


    private fun callInsomnia(topperData: TopperData) {
      Timber.tag("Timber").d("callInsomnia")

      if (topperData.HR != 0 && topperData.BR != 0) {
        if (topperData.HR - refHR > 5 || refHR - topperData.HR > 5) {
          saveDatabase(topperData)
        }
      } else {
//        WASO
        topperData.isSleep = false
        saveDatabase(topperData)
      }
    }

    private fun callDeepSleep(topperData: TopperData) {
      Timber.tag("Timber").d("callDeepSleep")
      var curTime = 0
      var sleepTime = 1
      var isWakeup = true
      if (curTime < sleepTime) {
        saveDatabase(topperData)
      } else {
        callAlarm()
        if (topperData.HR != 0 && topperData.BR != 0) {
          if (topperData.HR - refHR > 5 || refHR - topperData.HR > 5) {
            saveDatabase(topperData)
          }
        } else {
          topperData.isSleep = false
          saveDatabase(topperData)
        }
      }
    }

    fun callAlarm() {

    }

    fun saveDatabase(topperData: TopperData) {
      MainActivity.bleHandleService.insertNewTopperToDatabase(topperData)
    }
  }
}
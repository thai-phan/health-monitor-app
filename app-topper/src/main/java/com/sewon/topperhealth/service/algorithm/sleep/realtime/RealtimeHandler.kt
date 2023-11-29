package com.sewon.topperhealth.service.algorithm.sleep.realtime

import androidx.lifecycle.MutableLiveData
import com.sewon.topperhealth.MainActivity
import com.sewon.topperhealth.service.algorithm.sleep.AlgorithmConstants
import com.sewon.topperhealth.service.algorithm.sleep.TopperData
import com.sewon.topperhealth.service.bluetooth.LowEnergyService
import timber.log.Timber
import java.util.Date

class RealtimeHandler {


  companion object {
    val tag = "RealtimeHandler"
    private var referenceCount = 3 * 60 * 20

    fun receiveData(messageList: List<String>) {
      val topperData = TopperData(LowEnergyService.sessionId, messageList)
      processData(topperData)
//      MainActivity.lowEnergyService.insertNewTopperToDatabase(topperData)
    }

    fun resetData() {
      Timber.tag(tag).d("reset data")
      sumHRV = 0.0
      sumHR = 0.0
      sumBR = 0.0
      refHRV.value = 0.0
      refHR.value = 0.0
      refBR.value = 0.0
      countReferenceData.value = 0
      countInsomniaValue = 0
      countDeepSleep = 0
    }

    var sumHRV = 0.0
    var sumHR = 0.0
    var sumBR = 0.0
    var refHRV = MutableLiveData(0.0)
    var refHR = MutableLiveData(0.0)
    var refBR = MutableLiveData(0.0)
    var countReferenceData = MutableLiveData(0)

    private fun processData(topperData: TopperData) {
      countReferenceData.value = countReferenceData.value?.plus(1)
      if (countReferenceData.value!! < referenceCount) {
        sumHRV = sumHRV.plus(topperData.HRV)
        sumHR = sumHR.plus(topperData.HR)
        sumBR = sumBR.plus(topperData.BR)
      }

      if (countReferenceData.value == referenceCount) {
        refHRV.value = sumHRV / referenceCount
        refHR.value = sumHR / referenceCount
        refBR.value = sumBR / referenceCount
        MainActivity.lowEnergyService.updateCurrentSessionRefValue(
          refHRV.value!!, refHR.value!!, refBR.value!!
        )
      }

      var saveDone = false


      if (topperData.HRV > refHRV.value!! * AlgorithmConstants.REALTIME_HRV_THRESHOLD) {
        if (topperData.HR < refHR.value!! * AlgorithmConstants.REALTIME_HR_THRESHOLD) {
          if (topperData.BR < refBR.value!! * AlgorithmConstants.REALTIME_BR_THRESHOLD) {
            Timber.tag("RealtimeHandler").d("sleepStart")
            sleepStart(topperData)
            saveDone = true
          }
        }
      }

      if (!saveDone) {
        topperData.status = AlgorithmConstants.STATUS_MISS_THRESHOLD
        saveDatabase(topperData)
      }
    }

    private var countInsomniaValue = 0
    private var countDeepSleep = 0

    private fun sleepStart(topperData: TopperData) {
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
      Timber.tag(tag).d("callInsomnia")
      if (topperData.HR != 0 && topperData.BR != 0) {
        if (topperData.HR - refHR.value!! > 5 || refHR.value!! - topperData.HR > 5) {
          topperData.status = AlgorithmConstants.STATUS_INSOMIA_SLEEP
          saveDatabase(topperData)
        }
      } else {
//        WASO
        topperData.status = AlgorithmConstants.STATUS_WASO
        topperData.isSleep = false
        saveDatabase(topperData)
      }
    }

    private fun callDeepSleep(topperData: TopperData) {
      Timber.tag(tag).d("callDeepSleep")
      val curTime = Date()
      if (curTime < LowEnergyService.pickerEndTime) {
        saveDatabase(topperData)
      } else {
        if (topperData.HR != 0 && topperData.BR != 0) {
          if (topperData.HR - refHR.value!! > 5 || refHR.value!! - topperData.HR > 5) {
            topperData.status = AlgorithmConstants.STATUS_DEEP_SLEEP
            saveDatabase(topperData)
          }
        } else {
          topperData.status = AlgorithmConstants.STATUS_NOT_SLEEP
          topperData.isSleep = false
          saveDatabase(topperData)
        }
      }
    }

    private fun saveDatabase(topperData: TopperData) {
      MainActivity.lowEnergyService.insertNewTopperToDatabase(topperData)
    }
  }
}
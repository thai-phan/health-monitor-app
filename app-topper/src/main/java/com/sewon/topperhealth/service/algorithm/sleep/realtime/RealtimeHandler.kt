package com.sewon.topperhealth.service.algorithm.sleep.realtime

import androidx.lifecycle.MutableLiveData
import com.sewon.topperhealth.MainActivity
import com.sewon.topperhealth.service.algorithm.sleep.AlgorithmConstants
import com.sewon.topperhealth.service.algorithm.sleep.AlgorithmConstants.STABLE_MOVING
import com.sewon.topperhealth.service.algorithm.sleep.AlgorithmConstants.STABLE_NO_TARGET
import com.sewon.topperhealth.service.algorithm.sleep.AlgorithmConstants.STABLE_NO_VITAL_SIGN
import com.sewon.topperhealth.service.algorithm.sleep.TopperData
import com.sewon.topperhealth.service.bluetooth.LowEnergyService
import timber.log.Timber
import java.util.Date

class RealtimeHandler {


  companion object {
    const val tag = "TimberRealtimeHandler"
    private var referenceCount = 3 * 60 * 20

    private var ONE_MINUTE_COUNT = 20 * 60
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
      isRefCalculated = false
    }

    private var sumHRV = 0.0
    private var sumHR = 0.0
    private var sumBR = 0.0
    val refHRV = MutableLiveData(0.0)
    val refHR = MutableLiveData(0.0)
    val refBR = MutableLiveData(0.0)
    val countReferenceData = MutableLiveData(0)
    private var countInsomniaValue = 0
    private var countDeepSleep = 0
    private var isRefCalculated = false

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
        isRefCalculated = true
      }

      var saveDone = false

      if (!isRefCalculated) {
        saveDatabase(topperData, AlgorithmConstants.STATUS_CALCULATE_REF)
      } else {
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
          saveDatabase(topperData, AlgorithmConstants.STATUS_MISS_THRESHOLD)
        }
      }
    }

    private fun sleepStart(topperData: TopperData) {
      if (topperData.stable == STABLE_NO_TARGET || topperData.stable == STABLE_NO_VITAL_SIGN) {
        if (countInsomniaValue >= ONE_MINUTE_COUNT) {
          callInsomnia(topperData)
        } else {
          saveDatabase(topperData, AlgorithmConstants.STATUS_INSOMIA_SLEEP_ACCUMULATE)
        }
        countInsomniaValue += 1
      }


      if (topperData.stable == STABLE_MOVING) {
        if (countDeepSleep >= ONE_MINUTE_COUNT) {
          callDeepSleep(topperData)
        } else {
          saveDatabase(topperData, AlgorithmConstants.STATUS_DEEP_SLEEP_ACCUMULATE)
        }
        countDeepSleep += 1
      }
    }


    private fun callInsomnia(topperData: TopperData) {
      Timber.tag(tag).d("callInsomnia")
      if (topperData.HR != 0 && topperData.BR != 0) {
        if (topperData.HR - refHR.value!! > 5 || refHR.value!! - topperData.HR > 5) {
          saveDatabase(topperData, AlgorithmConstants.STATUS_INSOMIA_SLEEP)
        }
      } else {
        topperData.isSleep = false
        saveDatabase(topperData, AlgorithmConstants.STATUS_WASO)
      }
    }

    private fun callDeepSleep(topperData: TopperData) {
      Timber.tag(tag).d("callDeepSleep")
      val curTime = Date()
      if (curTime < LowEnergyService.pickerEndTime) {
        saveDatabase(topperData, AlgorithmConstants.STATUS_DEEP_SLEEP)
      } else {
        if (topperData.HR != 0 && topperData.BR != 0) {
          if (topperData.HR - refHR.value!! > 5 || refHR.value!! - topperData.HR > 5) {
            saveDatabase(topperData, AlgorithmConstants.STATUS_DEEP_SLEEP)
          }
        } else {
          topperData.isSleep = false
          saveDatabase(topperData, AlgorithmConstants.STATUS_NOT_SLEEP)
        }
      }
    }

    private fun saveDatabase(topperData: TopperData, status: String) {
      topperData.status = status
      MainActivity.lowEnergyService.insertNewTopperToDatabase(topperData)
    }
  }
}
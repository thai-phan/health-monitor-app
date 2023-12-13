package com.sewon.topperhealth.service.algorithm.sleep.report

import com.sewon.topperhealth.data.source.local.entity.LocalTopper
import com.sewon.topperhealth.service.algorithm.ecg.ECGAnalysisProc
import com.sewon.topperhealth.service.algorithm.ecg.ECGTopper
import com.sewon.topperhealth.service.algorithm.sleep.AlgorithmConstants


class ReportHandler(
  private var sessionData: List<LocalTopper>,
  var refHRV: Double,
  var refHR: Double,
  var refBR: Double
) {
  val tag = "TimberReportHandler"

  fun getBSleepRPI(): List<Float> {
    return sessionData.map { it.hrv.toFloat() }
  }

  fun getCMeanHR(): Float {
    return sessionData.map { it.hr }.average().toFloat()
  }

  fun getCMeanBR(): Float {
    return sessionData.map { it.br }.average().toFloat()
  }

  fun getECGAlgorithmResult(): ECGTopper {
    val HRVArray: DoubleArray = sessionData.map { it.hrv }.toDoubleArray()
    return ECGAnalysisProc.ECG_PPG_AnalysisData(HRVArray)
  }

  fun getRPITriangular(): Float {
    val HRVList = sessionData.map { it.hrv }
    return mostCommon(HRVList).toFloat()
  }

  private fun <T> mostCommon(list: List<T>): T {
    val map: MutableMap<T, Int> = HashMap()
    for (t in list) {
      val value = map[t]
      map[t] = if (value == null) 1 else value + 1
    }
    var max: Map.Entry<T, Int>? = null
    for (e in map.entries) {
      if ((max == null) || (e.value > max.value)) max = e
    }
    return max!!.key
  }


  fun getSleepStage(): List<Float> {
    var sumHRV = 0.0
    var sumHR = 0.0
    var sumBR = 0.0
    val stageList = mutableListOf<Float>()
    var countTime = 0

    for (data in sessionData) {
      if (countTime == AlgorithmConstants.SLEEP_STAGE_NUMBER) {
        val meanHRV = sumHRV / countTime
        val meanHR = sumHR / countTime
        val meanBR = sumBR / countTime
        val stage = calculatorSleepStage(meanHRV, meanHR, meanBR)
        stageList.add(stage.toFloat())

        sumHRV = 0.0
        sumHR = 0.0
        sumBR = 0.0
        countTime = 0
      }
      sumHRV += data.hrv
      sumHR += data.hr
      sumBR += data.br
      countTime += 1
    }
    return stageList
  }

  private fun calculatorSleepStage(meanHrv: Double, meanHr: Double, meanBr: Double): Int {
    if (meanHrv < refHRV * AlgorithmConstants.SLEEP_HRV_THRESHOLD) {
      return AlgorithmConstants.SLEEP_STAGE_N3_REM
    } else {
      return if (meanHr >= refHR * AlgorithmConstants.SLEEP_HR_THRESHOLD) {
        if (meanBr >= refBR * AlgorithmConstants.SLEEP_HRUP_BR_THRESHOLD) {
          AlgorithmConstants.SLEEP_STAGE_N1_N2
        } else {
          AlgorithmConstants.SLEEP_STAGE_N3_REM
        }
      } else {
        if (meanBr >= refBR * AlgorithmConstants.SLEEP_HRDOWN_BR_THRESHOLD) {
          AlgorithmConstants.SLEEP_STAGE_WAKE
        } else {
          AlgorithmConstants.SLEEP_STAGE_N1_N2
        }
      }
    }
  }

}
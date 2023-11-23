package com.sewon.topperhealth.service.algorithm.sleep.report

import com.sewon.topperhealth.data.source.local.entity.LocalTopper
import com.sewon.topperhealth.service.algorithm.ecg.ECGAnalysisProc
import com.sewon.topperhealth.service.algorithm.ecg.ECGTopper
import com.sewon.topperhealth.service.algorithm.sleep.AlgorithmConstants


class ReportProcess {

  companion object {

    var refHRV = 0.0
    var refHR = 0.0
    var refBR = 0.0

    lateinit var allData: List<LocalTopper>

    fun importData(data: List<LocalTopper>) {
      allData = data
    }

    fun getBSleepRPI(): List<Float> {
      return allData.map { it.hrv.toFloat() }
    }

    fun getCMeanHR(): Float {
      return allData.map { it.hr }.average().toFloat()
    }

    fun getCMeanBR(): Float {
      return allData.map { it.br }.average().toFloat()
    }
    
    fun getECGAlgorithmResult(): ECGTopper {
      val HRVArray: DoubleArray = allData.map { it.hrv }.toDoubleArray()
      return ECGAnalysisProc.ECG_PPG_AnalysisData(HRVArray)
    }

    fun getRPITriangular(): Float {
      val HRVList = allData.map { it.hrv }
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

      for (data in allData) {
        if (countTime == AlgorithmConstants.SLEEP_STAGE_NUMBER) {
          val meanHRV = sumHRV / countTime
          val meanHR = sumHR / countTime
          val meanBR = sumBR / countTime
          val stage = processReport(meanHRV, meanHR, meanBR)
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

    private fun processReport(meanHrv: Double, meanHr: Double, meanBr: Double): Int {
      if (meanHrv < refHRV * AlgorithmConstants.REPORT_HRV_THRESHOLD) {
        return AlgorithmConstants.REPORT_STAGE_REM
      } else {
        if (meanHr >= refHR * AlgorithmConstants.REPORT_HR_THRESHOLD) {
          if (meanBr >= refBR * AlgorithmConstants.REPORT_HRUP_BR_THRESHOLD) {
            return AlgorithmConstants.REPORT_STAGE_N2
          } else {
            return AlgorithmConstants.REPORT_STAGE_N3
          }
        } else {
          if (meanBr >= refBR * AlgorithmConstants.REPORT_HRDOWN_BR_THRESHOLD) {
            return AlgorithmConstants.REPORT_STAGE_WAKE
          } else {
            return AlgorithmConstants.REPORT_STAGE_N1
          }
        }
      }
    }
  }
}
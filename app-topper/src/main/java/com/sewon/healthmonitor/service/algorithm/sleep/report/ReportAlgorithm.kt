package com.sewon.healthmonitor.service.algorithm.sleep.report

import com.sewon.healthmonitor.service.algorithm.sleep.AlgorithmConstants


class ReportAlgorithm {
  companion object {
    var refHRV = 0.0
    var refHR = 0.0
    var refBR = 0.0

    fun processByTime(): List<Float> {
      var sumHRV = 0.0
      var sumHR = 0.0
      var sumBR = 0.0
      val stageList = mutableListOf<Float>()
      var countTime = 0

      for (data in ReportDataProcessing.allData) {
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

    fun processReport(meanHrv: Double, meanHr: Double, meanBr: Double): Int {
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
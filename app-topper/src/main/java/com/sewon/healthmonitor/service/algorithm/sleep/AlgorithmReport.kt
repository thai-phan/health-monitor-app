package com.sewon.healthmonitor.service.algorithm.sleep

import com.sewon.healthmonitor.service.algorithm.sleep.AlgorithmRealtime.Companion.firstMeanBR
import com.sewon.healthmonitor.service.algorithm.sleep.AlgorithmRealtime.Companion.firstMeanHR
import com.sewon.healthmonitor.service.algorithm.sleep.AlgorithmRealtime.Companion.firstMeanHRV

class AlgorithmReport {
  companion object {
    var meanHrv = 0.0
    var meanHr = 0.0
    var meanBr = 0.0

    var HrvTheshold = 0.9
    var HrTheshold = 0.9
    var HrUpBrThreshold = 0.9
    var HrDownBrThreshold = 1.2

    var countREM = 0
    var countN1 = 0
    var countN2 = 0
    var countN3 = 0
    var countWake = 0


    fun processReport() {
      if (meanHrv < firstMeanHRV * Constants.REPORT_HRV_THRESHOLD) {
        countREM + 1
      } else {
        if (meanHr >= firstMeanHR * Constants.REPORT_HR_THRESHOLD) {
          if (meanBr >= firstMeanBR * Constants.REPORT_HRUP_BR_THRESHOLD) {
            countN2 += 1
          } else {
            countN3 += 1
          }
        } else {
          if (meanBr >= firstMeanBR * Constants.REPORT_HRDOWN_BR_THRESHOLD) {
            countWake += 1
          } else {
            countN1 += 1
          }
        }
      }
    }
  }
}
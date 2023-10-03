package com.sewon.healthmonitor.service.algorithm.sleep

object AlgorithmConstants {
  const val REALTIME_HRV_THRESHOLD = 1.2
  const val REALTIME_HR_THRESHOLD = 0.9
  const val REALTIME_BR_THRESHOLD = 1

  const val REPORT_STAGE_WAKE = 4
  const val REPORT_STAGE_REM = 3
  const val REPORT_STAGE_N1 = 2
  const val REPORT_STAGE_N2 = 1
  const val REPORT_STAGE_N3 = 0

  const val REPORT_HRV_THRESHOLD = 0.9
  const val REPORT_HR_THRESHOLD = 0.9
  const val REPORT_HRUP_BR_THRESHOLD = 0.9
  const val REPORT_HRDOWN_BR_THRESHOLD = 1.2
}
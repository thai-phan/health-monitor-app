package com.sewon.topperhealth.service.algorithm.sleep

object AlgorithmConstants {
  const val REALTIME_HRV_THRESHOLD = 1.2
  const val REALTIME_HR_THRESHOLD = 0.9
  const val REALTIME_BR_THRESHOLD = 1.0

  const val SLEEP_STAGE_WAKE = 4
  const val SLEEP_STAGE_REM = 3
  const val SLEEP_STAGE_N1 = 2
  const val SLEEP_STAGE_N2 = 1
  const val SLEEP_STAGE_N3 = 0

  const val SLEEP_HRV_THRESHOLD = 0.9
  const val SLEEP_HR_THRESHOLD = 0.9
  const val SLEEP_HRUP_BR_THRESHOLD = 0.9
  const val SLEEP_HRDOWN_BR_THRESHOLD = 1.2

  const val SLEEP_STAGE_NUMBER = 100
}
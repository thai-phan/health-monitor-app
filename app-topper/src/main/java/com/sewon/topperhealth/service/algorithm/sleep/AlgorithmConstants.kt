package com.sewon.topperhealth.service.algorithm.sleep

object AlgorithmConstants {
  const val REALTIME_HRV_THRESHOLD = 1.2
  const val REALTIME_HR_THRESHOLD = 0.9
  const val REALTIME_BR_THRESHOLD = 1.0

  const val STABLE_NO_TARGET = 0
  const val STABLE_NO_VITAL_SIGN = 1
  const val STABLE_MOVING = 2
  const val STABLE_BR_LOWER_50 = 3
  const val STABLE_BR_UPPER_50 = 4

  const val NO_VITAL_SIGN_THRESHOLD = 1200
  const val NO_TARGET_THRESHOLD = 1200

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

  const val STATUS_CALCULATE_REF = "Calculate reference"
  const val STATUS_MISS_THRESHOLD = "Miss threshold"
  const val STATUS_INSOMIA_SLEEP = "Insomnia Sleep"
  const val STATUS_INSOMIA_SLEEP_ACCUMULATE = "Insomnia Sleep Accumulate"
  const val STATUS_DEEP_SLEEP = "Deep Sleep"
  const val STATUS_DEEP_SLEEP_ACCUMULATE = "Deep Sleep Accumulate"

  const val STATUS_NOT_SLEEP = "Not Sleep"
  const val STATUS_WASO = "WASO"
}
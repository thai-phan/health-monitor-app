package com.sewon.topperhealth.data.model

import com.sewon.topperhealth.data.source.local.entity.LocalSleepSession
import java.util.Date

data class SleepSession(
  val refHRV: Double = 0.0,
  val refHR: Double = 0.0,
  val refBR: Double = 0.0,
  val isGeneratedReport: Boolean = false,
  val meanHR: Double = 0.0,
  val meanBR: Double = 0.0,
  val SDRP: Double = 0.0,
  val RMSSD: Double = 0.0,
  val RPITriangular: Double = 0.0,
  val lowFreq: Double = 0.0,
  val highFreq: Double = 0.0,
  val lfHfRatio: Double = 0.0,
  val rating: Int = 0,
  val wakeUpCount: Int = 0,
  val pickerStartTime: Date,
  val pickerEndTime: Date,
  val actualStartTime: Date = Date(),
  val actualEndTime: Date = Date(),
  val fellAsleepTime: Date = Date(),
  val assessment: String = "",
  val memo: String = "",
  val sessionId: Int = -1,
)

fun SleepSession.toLocal() = LocalSleepSession(
  refHRV = refHRV,
  refHR = refHR,
  refBR = refBR,
  isGeneratedReport = isGeneratedReport,

  meanHR = meanHR,
  meanBR = meanBR,
  SDRP = SDRP,
  RMSSD = RMSSD,
  RPITriangular = RPITriangular,
  lowFreq = lowFreq,
  highFreq = highFreq,
  lfHfRatio = lfHfRatio,

  rating = rating,
  wakeUpCount = wakeUpCount,

  pickerStartTime = pickerStartTime,
  pickerEndTime = pickerEndTime,
  actualStartTime = actualStartTime,
  actualEndTime = actualEndTime,
  fellAsleepTime = fellAsleepTime,

  assessment = assessment,
  memo = memo
)

fun List<SleepSession>.toLocal() = map(SleepSession::toLocal)


fun LocalSleepSession.toExternal() = SleepSession(
  refHRV = refHRV,
  refHR = refHR,
  refBR = refBR,
  isGeneratedReport = isGeneratedReport,

  meanHR = meanHR,
  meanBR = meanBR,
  SDRP = SDRP,
  RMSSD = RMSSD,
  RPITriangular = RPITriangular,
  lowFreq = lowFreq,
  highFreq = highFreq,
  lfHfRatio = lfHfRatio,

  rating = rating,
  wakeUpCount = wakeUpCount,

  pickerStartTime = pickerStartTime,
  pickerEndTime = pickerEndTime,
  actualStartTime = actualStartTime,
  actualEndTime = actualEndTime,
  fellAsleepTime = fellAsleepTime,
  sessionId = sessionId,

  assessment = assessment,
  memo = memo
)

fun List<LocalSleepSession>.toExternal() = map(LocalSleepSession::toExternal)
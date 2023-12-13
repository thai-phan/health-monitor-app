package com.sewon.topperhealth.data.model

import com.sewon.topperhealth.data.source.local.entity.LocalSleepSession
import java.util.Date

data class SleepSession(
  val refHRV: Double,
  val refHR: Double,
  val refBR: Double,
  val isGeneratedReport: Boolean,

  val meanHR: Double,
  val meanBR: Double,
  val SDRP: Double,
  val RMSSD: Double,
  val RPITriangular: Double,
  val lowFreq: Double,
  val highFreq: Double,
  val lfHfRatio: Double,

  val rating: Int,
  val wakeUpCount: Int,

  val pickerStartTime: Date,
  val pickerEndTime: Date,
  val actualStartTime: Date,
  val actualEndTime: Date,
  val fellAsleepTime: Date,

  val assessment: String,
  val memo: String,

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
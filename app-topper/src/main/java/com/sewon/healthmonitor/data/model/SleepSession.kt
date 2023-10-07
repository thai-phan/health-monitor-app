package com.sewon.healthmonitor.data.model

import com.sewon.healthmonitor.data.source.local.entity.LocalSleepSession
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
  val sleepTime: Date,
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
  sleepTime = sleepTime,
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
  sleepTime = sleepTime,
  sessionId = sessionId,
)

fun List<LocalSleepSession>.toExternal() = map(LocalSleepSession::toExternal)
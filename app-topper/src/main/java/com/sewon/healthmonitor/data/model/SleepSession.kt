package com.sewon.healthmonitor.data.model

import com.sewon.healthmonitor.data.source.local.entity.LocalSleepSession
import java.time.LocalTime
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
  val LfHfRatio: Double,

  val rating: Int,
  val wakeUpCount: Int,
  val selectedStartTime: Date,
  val actualStartTime: Date,
  val sleepTime: Date,
  val endTime: Date,
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
  LfHfRatio = LfHfRatio,

  rating = rating,
  wakeUpCount = wakeUpCount,
  selectedStartTime = selectedStartTime,
  actualStartTime = actualStartTime,
  sleepTime = sleepTime,
  endTime = endTime,
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
  LfHfRatio = LfHfRatio,

  rating = rating,
  wakeUpCount = wakeUpCount,
  selectedStartTime = selectedStartTime,
  actualStartTime = actualStartTime,
  sleepTime = sleepTime,
  endTime = endTime
)

fun List<LocalSleepSession>.toExternal() = map(LocalSleepSession::toExternal)
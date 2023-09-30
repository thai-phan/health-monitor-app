package com.sewon.healthmonitor.data.model

import androidx.room.ColumnInfo
import com.sewon.healthmonitor.data.source.local.entity.LocalSession
import java.time.LocalTime

data class Session(
  val refHRV: Double,
  val refHR: Double,
  val refBR: Double,

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
  val startTime: LocalTime,
  val sleepTime: LocalTime,
  val endTime: LocalTime,
)

fun Session.toLocal() = LocalSession(
  refHRV = refHRV,
  refHR = refHR,
  refBR = refBR,


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
  startTime = startTime,
  sleepTime = sleepTime,
  endTime = endTime,


  )

fun List<Session>.toLocal() = map(Session::toLocal)


fun LocalSession.toExternal() = Session(
  refHRV = refHRV,
  refHR = refHR,
  refBR = refBR,


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
  startTime = startTime,
  sleepTime = sleepTime,
  endTime = endTime,

  )

fun List<LocalSession>.toExternal() = map(LocalSession::toExternal)
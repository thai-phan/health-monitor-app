package com.sewon.healthmonitor.data.model

import com.sewon.healthmonitor.data.source.local.entity.LocalSession
import java.time.LocalTime

data class Session(
  var meanHRV: Double,
  var meanHR: Double,
  var meanBR: Double,

  val rating: Int,
  val wakeUpCount: Int,
  val startTime: LocalTime,
  val sleepTime: LocalTime,
  val endTime: LocalTime,
)

fun Session.toLocal() = LocalSession(
  meanHRV = meanHRV,
  meanHR = meanHR,
  meanBR = meanBR,
  rating = rating,
  wakeUpCount = wakeUpCount,
  startTime = startTime,
  sleepTime = sleepTime,
  endTime = endTime,
)

fun List<Session>.toLocal() = map(Session::toLocal)


fun LocalSession.toExternal() = Session(
  meanHRV = meanHRV,
  meanHR = meanHR,
  meanBR = meanBR,
  rating = rating,
  wakeUpCount = wakeUpCount,
  startTime = startTime,
  sleepTime = sleepTime,
  endTime = endTime,

  )

fun List<LocalSession>.toExternal() = map(LocalSession::toExternal)
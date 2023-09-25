package com.sewon.healthmonitor.data.model

import com.sewon.healthmonitor.data.source.local.entity.LocalSession
import java.time.LocalTime

data class Session(
  val createdAt: LocalTime,
  val finishedAt: LocalTime,
)

fun Session.toLocal() = LocalSession(
  createdAt = createdAt,
  finishedAt = finishedAt
)

fun List<Session>.toLocal() = map(Session::toLocal)


fun LocalSession.toExternal() = Session(
  createdAt = createdAt,
  finishedAt = finishedAt
)

fun List<LocalSession>.toExternal() = map(LocalSession::toExternal)
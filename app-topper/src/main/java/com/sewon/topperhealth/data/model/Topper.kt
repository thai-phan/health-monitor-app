package com.sewon.topperhealth.data.model

import com.sewon.topperhealth.data.source.local.entity.LocalTopper
import java.time.LocalTime


data class Topper(
  val stable: Int,
  val hr: Int,
  var br: Int,
  val hrv: Double,
  val hrWfm: Double,
  val brWfm: Double,
  val isSleep: Boolean,
  val status: String,
  val sessionId: Int,
  val createdAt: LocalTime,
)


// Model mapping
// extension function
fun Topper.toLocal() = LocalTopper(
  stable = stable,
  hr = hr,
  br = br,
  hrv = hrv,
  hrWfm = hrWfm,
  brWfm = brWfm,
  isSleep = isSleep,
  status = status,
  createdAt = createdAt,
  sessionId = sessionId
)

fun List<Topper>.toLocal() = map(Topper::toLocal)

fun LocalTopper.toExternal() = Topper(
  stable = stable,
  hr = hr,
  br = br,
  hrv = hrv,
  hrWfm = hrWfm,
  brWfm = brWfm,
  isSleep = isSleep,
  status = status,
  sessionId = sessionId,
  createdAt = createdAt
)

fun List<LocalTopper>.toExternal() = map(LocalTopper::toExternal)



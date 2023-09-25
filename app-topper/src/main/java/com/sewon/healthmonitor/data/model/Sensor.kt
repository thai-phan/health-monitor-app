package com.sewon.healthmonitor.data.model

import com.sewon.healthmonitor.data.source.local.entity.LocalSensor
import java.time.LocalTime


data class Sensor(
  val stable: Int,
  val hr: Int,
  var br: Int,
  val hrv: Double,
  val hrWfm: Double,
  val brWfm: Double,
  val isSleep: Boolean,
  val sessionId: Int,
  val createdAt: LocalTime,
)


// Model mapping
// extension function
fun Sensor.toLocal() = LocalSensor(
  stable = stable,
  hr = hr,
  br = br,
  hrv = hrv,
  hrWfm = hrWfm,
  brWfm = brWfm,
  isSleep = isSleep,
  createdAt = createdAt,
  sessionId = sessionId
)

fun List<Sensor>.toLocal() = map(Sensor::toLocal)

fun LocalSensor.toExternal() = Sensor(
  stable = stable,
  hr = hr,
  br = br,
  hrv = hrv,
  hrWfm = hrWfm,
  brWfm = brWfm,
  isSleep = isSleep,
  sessionId = sessionId,
  createdAt = createdAt
)

fun List<LocalSensor>.toExternal() = map(LocalSensor::toExternal)



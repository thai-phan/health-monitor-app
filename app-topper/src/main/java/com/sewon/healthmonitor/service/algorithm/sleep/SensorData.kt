package com.sewon.healthmonitor.service.algorithm.sleep

import com.sewon.healthmonitor.data.model.Sensor
import java.time.LocalTime

class SensorData(stringList: List<String>) {
  var stable: Int
  var HR: Int
  var BR: Int
  var HRV: Double
  var HRWfm: Double
  var BRWfm: Double
  var isSleep: Boolean
  var sessionId: Int
  var createdAt: LocalTime

  init {
    stable = stringList[0].toInt()
    HR = stringList[1].toInt()
    BR = stringList[2].toInt()
    HRV = stringList[3].toDouble()
    HRWfm = stringList[4].toDouble()
    BRWfm = stringList[5].toDouble()
    isSleep = true
    sessionId = 1
    createdAt = LocalTime.now()
  }

  fun toSensorModel(): Sensor {
    return Sensor(
      stable = stable,
      hr = HR,
      br = BR,
      hrv = HRV,
      hrWfm = HRWfm,
      brWfm = BRWfm,
      isSleep = isSleep,
      sessionId = sessionId,
      createdAt = createdAt
    )
  }
}



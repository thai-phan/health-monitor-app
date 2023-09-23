package com.sewon.healthmonitor.algorithm.sleep

import com.sewon.healthmonitor.data.model.Sensor

class SensorData(stringList: List<String>) {
  var stable: Int
  var HR: Int
  var BR: Int
  var HRV: Double
  var HRWfm: Double
  var BRWfm: Double
  var isSleep: Boolean

  init {
    stable = stringList[0].toInt()
    HR = stringList[1].toInt()
    BR = stringList[2].toInt()
    HRV = stringList[3].toDouble()
    HRWfm = stringList[4].toDouble()
    BRWfm = stringList[5].toDouble()
    isSleep = true
  }

  fun toSensorModel(): Sensor {
    return Sensor(
      stable = stable,
      hr = HR,
      br = BR,
      hrv = HRV,
      hrWfm = HRWfm,
      brWfm = BRWfm,
      isSleep = isSleep
    )
  }
}



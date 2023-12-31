package com.sewon.topperhealth.service.algorithm.sleep

import com.sewon.topperhealth.data.model.Topper
import java.lang.NumberFormatException
import java.time.LocalTime

class TopperData(currentSessionId: Int, stringList: List<String>) {
  var stable: Int
  var HR: Int
  var BR: Int
  var HRV: Double
  var HRWfm: Double
  var BRWfm: Double
  var isSleep: Boolean
  var status: String
  var sessionId: Int = 0
  var createdAt: LocalTime

  init {
    stable = stringList[0].toInt()
    HR = stringList[1].toInt()
    BR = stringList[2].toInt()
    HRV = stringList[3].toDouble()
    HRWfm = try {
      stringList[4].toDouble()
    } catch (error: NumberFormatException) {
      0.0
    }
    BRWfm = try {
      stringList[5].toDouble()
    } catch (error: NumberFormatException) {
      0.0
    }
    isSleep = true
    status = ""
    sessionId = currentSessionId
    createdAt = LocalTime.now()
  }

  fun toTopperModel(): Topper {
    return Topper(
      stable = stable,
      hr = HR,
      br = BR,
      hrv = HRV,
      hrWfm = HRWfm,
      brWfm = BRWfm,
      isSleep = isSleep,
      status = status,
      sessionId = sessionId,
      createdAt = createdAt
    )
  }
}



package com.sewon.healthmonitor.data.repository.repointerface

import com.sewon.healthmonitor.data.model.SleepSession
import java.util.Date


interface ISessionRepository {

  suspend fun getSleepSessionList(): List<SleepSession>

  suspend fun getSessionById(id: Int): SleepSession?

  suspend fun createNewSession(sleepSession: SleepSession): Long

  suspend fun countSession(): Int

  // TODO:
  suspend fun updateSessionRefValue(sessionId: Int, refHRV: Double, refHR: Double, refBR: Double)

  // TODO:
  suspend fun updateSessionEndTime(sessionId: Int, endTime: Date)
}
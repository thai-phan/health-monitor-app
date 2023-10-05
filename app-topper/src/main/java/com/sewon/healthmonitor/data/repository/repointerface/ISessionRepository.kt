package com.sewon.healthmonitor.data.repository.repointerface

import com.sewon.healthmonitor.data.model.SleepSession
import com.sewon.healthmonitor.data.source.local.entity.LocalSleepSession


interface ISessionRepository {

  suspend fun getSessionById(id: Int): LocalSleepSession?

  suspend fun addSession(sleepSession: SleepSession): Long

  suspend fun countSession(): Int

  fun updateSessionRefValue(refHRV: Double, refHR: Double, refBR: Double, sessionId: Int)
}
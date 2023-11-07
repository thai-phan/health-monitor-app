package com.sewon.topperhealth.data.irepository

import com.sewon.topperhealth.data.model.SleepSession
import java.util.Date


interface ISessionRepository {

  suspend fun getSleepSessionList(): List<SleepSession>

  suspend fun getSessionById(id: Int): SleepSession?

  suspend fun createNewSession(sleepSession: SleepSession): Long

  suspend fun countSession(): Int

  suspend fun updateSessionRefValue(sessionId: Int, refHRV: Double, refHR: Double, refBR: Double)

  suspend fun updateSessionEndTime(sessionId: Int, endTime: Date)

  suspend fun updateSessionAssessment(sessionId: Int, assessment: String)

  suspend fun updateSessionQualityMemo(sessionId: Int, rating: Int, memo: String)

  suspend fun deleteAll(): Int
}
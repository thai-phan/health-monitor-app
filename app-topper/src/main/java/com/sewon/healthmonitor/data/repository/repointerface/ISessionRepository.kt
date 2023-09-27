package com.sewon.healthmonitor.data.repository.repointerface

import com.sewon.healthmonitor.data.model.Session
import com.sewon.healthmonitor.data.source.local.entity.LocalSession
import kotlinx.coroutines.flow.Flow


interface ISessionRepository {

  suspend fun getSessionById(id: Int): LocalSession

  fun getCountTopper(): Flow<Int>

  suspend fun addSession(session: Session)

  suspend fun countSession(): Flow<Int>

  suspend fun createTopper(localSensor: LocalSession): String
}
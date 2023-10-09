package com.sewon.healthmonitor.data.source.local.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.model.SleepSession
import com.sewon.healthmonitor.data.model.toExternal
import com.sewon.healthmonitor.data.model.toLocal
import com.sewon.healthmonitor.data.irepository.ISessionRepository
import com.sewon.healthmonitor.data.source.local.dao.LocalSessionDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import java.util.Date
import javax.inject.Inject


class SessionRepository @Inject constructor(
  private val localSessionDao: LocalSessionDao,
  @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
  @ApplicationScope private val scope: CoroutineScope,
) : ISessionRepository {
  override suspend fun getSleepSessionList(): List<SleepSession> {
    return localSessionDao.getAllSession().toExternal()
  }

  override suspend fun getSessionById(id: Int): SleepSession? {
    return localSessionDao.loadById(id)?.toExternal()
  }

  override suspend fun countSession(): Int {
    return localSessionDao.countSession()
  }

  override suspend fun updateSessionRefValue(
    sessionId: Int,
    refHRV: Double,
    refHR: Double,
    refBR: Double
  ) {
    return localSessionDao.queryUpdateSessionRefValue(sessionId, refHRV, refHR, refBR)

  }

  override suspend fun updateSessionEndTime(sessionId: Int, endTime: Date) {
    return localSessionDao.queryUpdateSessionEndTime(sessionId, endTime)
  }

  override suspend fun createNewSession(sleepSession: SleepSession): Long {
    return localSessionDao.insert(sleepSession.toLocal())
  }
}
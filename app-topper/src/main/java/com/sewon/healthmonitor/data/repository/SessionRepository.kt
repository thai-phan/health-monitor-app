package com.sewon.healthmonitor.data.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.model.SleepSession
import com.sewon.healthmonitor.data.model.toLocal
import com.sewon.healthmonitor.data.repository.repointerface.ISessionRepository
import com.sewon.healthmonitor.data.source.local.dao.LocalSessionDao
import com.sewon.healthmonitor.data.source.local.entity.LocalSleepSession
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


class SessionRepository @Inject constructor(
  private val localSessionDao: LocalSessionDao,
  @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
  @ApplicationScope private val scope: CoroutineScope,
) : ISessionRepository {

  override suspend fun getSessionById(id: Int): LocalSleepSession? {
    return localSessionDao.loadById(id)
  }

  override suspend fun countSession(): Int {
    return localSessionDao.countSession()
  }

  override fun updateSessionRefValue(refHRV: Double, refHR: Double, refBR: Double, sessionId: Int) {
    return localSessionDao.queryUpdateSessionRefValue(refHRV, refHR, refBR, sessionId)

  }

  override suspend fun addSession(sleepSession: SleepSession): Long {
    return localSessionDao.insert(sleepSession.toLocal())
  }
}
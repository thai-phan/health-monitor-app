package com.sewon.healthmonitor.data.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.model.Session
import com.sewon.healthmonitor.data.model.toLocal
import com.sewon.healthmonitor.data.repository.repointerface.ISensorRepository
import com.sewon.healthmonitor.data.repository.repointerface.ISessionRepository
import com.sewon.healthmonitor.data.source.local.dao.LocalSensorDao
import com.sewon.healthmonitor.data.source.local.dao.LocalSessionDao
import com.sewon.healthmonitor.data.source.local.entity.LocalSensor
import com.sewon.healthmonitor.data.source.local.entity.LocalSession
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SessionRepository @Inject constructor(
  private val localSessionDao: LocalSessionDao,
  @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
  @ApplicationScope private val scope: CoroutineScope,
) : ISessionRepository {

  override suspend fun getSessionById(id: Int): LocalSession {
    return localSessionDao.loadById(id)
  }

  override suspend fun countSession(): Flow<Int> {
    return localSessionDao.countSession()
  }

  override suspend fun addSession(session: Session) {
    return localSessionDao.insert(session.toLocal())
  }

  override fun getCountTopper(): Flow<Int> {
    TODO("Not yet implemented")
  }

  override suspend fun createTopper(localSensor: LocalSession): String {
    TODO("Not yet implemented")
  }

}
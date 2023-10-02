package com.sewon.healthmonitor.data.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.source.local.dao.LocalSensorDao
import com.sewon.healthmonitor.data.source.local.entity.LocalSensor
import com.sewon.healthmonitor.data.repository.repointerface.ISensorRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class SensorRepository @Inject constructor(
  private val LocalSensorDao: LocalSensorDao,
  @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
  @ApplicationScope private val scope: CoroutineScope,
) : ISensorRepository {


  override fun getAllDataFromSession(sessionId: Int): List<LocalSensor> {
    return LocalSensorDao.queryAllDataFromSession(sessionId)
  }


  override fun getDataCount(): Int {
    return LocalSensorDao.queryCountData()
  }
//     Count number record


  override suspend fun addSensorData(localSensor: LocalSensor): String {
    var aaa = LocalSensorDao.insert(localSensor)
    return "true"
  }

}
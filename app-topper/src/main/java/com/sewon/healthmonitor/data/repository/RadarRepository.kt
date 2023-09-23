package com.sewon.healthmonitor.data.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.source.local.dao.LocalSensorDao
import com.sewon.healthmonitor.data.source.local.entity.LocalSensor
import com.sewon.healthmonitor.data.repository.repointerface.IRadarRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RadarRepository @Inject constructor(
  private val LocalSensorDao: LocalSensorDao,
  @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
  @ApplicationScope private val scope: CoroutineScope,
) : IRadarRepository {


  override fun getTopper(): Flow<List<LocalSensor>> {
    return LocalSensorDao.getAllTopper()
  }


  override fun getCountTopper(): Flow<Int> {
    return LocalSensorDao.countTopper()
  }
//     Count number record


  override fun createTopper(localSensor: LocalSensor): String {
    LocalSensorDao.insert(localSensor)
    return "true"
  }

}
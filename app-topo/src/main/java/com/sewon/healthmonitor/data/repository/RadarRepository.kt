package com.sewon.healthmonitor.data.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.source.local.dao.LocalRadarDao
import com.sewon.healthmonitor.data.source.local.entity.LocalRadar
import com.sewon.healthmonitor.data.repository.repointerface.IRadarRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RadarRepository @Inject constructor(
  private val LocalRadarDao: LocalRadarDao,
  @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
  @ApplicationScope private val scope: CoroutineScope,
) : IRadarRepository {


  override fun getTopper(): Flow<List<LocalRadar>> {
    return LocalRadarDao.getAllTopper()
  }


  override fun getCountTopper(): Flow<Int> {
    return LocalRadarDao.countTopper()
  }
//     Count number record


  override fun createTopper(localRadar: LocalRadar): String {
    LocalRadarDao.insert(localRadar)
    return "true"
  }

}
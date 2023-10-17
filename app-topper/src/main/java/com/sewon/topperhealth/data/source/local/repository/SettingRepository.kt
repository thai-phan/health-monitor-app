package com.sewon.topperhealth.data.source.local.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.topperhealth.data.model.Setting
import com.sewon.topperhealth.data.model.toExternal
import com.sewon.topperhealth.data.source.local.dao.LocalSettingDao
import com.sewon.topperhealth.data.irepository.ISettingRepository
import com.sewon.topperhealth.data.model.toLocal
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalTime
import javax.inject.Inject

class SettingRepository @Inject constructor(
  private val localSettingDao: LocalSettingDao,
  @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
  @ApplicationScope private val scope: CoroutineScope,
) : ISettingRepository {

  override suspend fun addSetting(setting: Setting): Long {
    return localSettingDao.insert(setting.toLocal())
  }

  override fun flowLoadUserSetting(userId: Int): Flow<Setting> {
    return localSettingDao.querySettingByUserIdFlow(userId).map { it.toExternal() }
  }

  override suspend fun loadUserSetting(userId: Int): Setting? {
    return localSettingDao.querySettingByUserId(userId)?.toExternal()
  }

  override suspend fun updateAlarmOnSetting(userId: Int, alarmOn: Boolean): Int {
    return localSettingDao.updateAlarmOnQuery(userId, alarmOn)
  }

  override suspend fun updateAlarmTimeSetting(userId: Int, alarmTime: LocalTime): Int {
    return localSettingDao.updateAlarmTimeQuery(userId, alarmTime)
  }

  override suspend fun updateBedSetting(userId: Int, bedOn: Boolean): Int {
    return localSettingDao.updateBedSettingQuery(userId, bedOn)
  }

  override suspend fun updateBedTimeSetting(userId: Int, bedTime: LocalTime): Int {
    return localSettingDao.updateBedTimeQuery(userId, bedTime)
  }

  override suspend fun updateAlarmTypeSetting(userId: Int, alarmSetting: String): Int {
    return localSettingDao.updateAlarmTypeSettingQuery(userId, alarmSetting)
  }

  suspend fun countSetting(): Int {
    return localSettingDao.countSetting()
  }
}
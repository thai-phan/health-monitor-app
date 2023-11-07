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
    return localSettingDao.queryUpdateAlarmOnQuery(userId, alarmOn)
  }

  override suspend fun updateWakeupTimeSetting(userId: Int, wakeupTime: LocalTime): Int {
    return localSettingDao.queryUpdateWakeupTimeQuery(userId, wakeupTime)
  }

  override suspend fun updateBedSetting(userId: Int, bedOn: Boolean): Int {
    return localSettingDao.queryUpdateBedSettingQuery(userId, bedOn)
  }

  override suspend fun updateSleepTimeSetting(userId: Int, sleepTime: LocalTime): Int {
    return localSettingDao.queryUpdateSleepTimeQuery(userId, sleepTime)
  }

  override suspend fun updateAlarmTypeBehavior(userId: Int, alarmBehavior: String): Int {
    return localSettingDao.queryUpdateAlarmTypeSettingQuery(userId, alarmBehavior)
  }

  override suspend fun updateRecipientList(
    userId: Int,
    relation1: String, contact1: String,
    relation2: String, contact2: String,
    relation3: String, contact3: String
  ): Int {
    return localSettingDao.queryUpdateRecipient(
      userId,
      relation1, contact1,
      relation2, contact2,
      relation3, contact3
    )
  }

  suspend fun countSetting(): Int {
    return localSettingDao.countSetting()
  }
}
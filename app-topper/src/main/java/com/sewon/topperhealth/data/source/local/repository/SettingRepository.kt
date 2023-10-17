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

  suspend fun addSetting(setting: Setting) {
    return localSettingDao.upsert(setting.toLocal())
  }

  override fun loadUserSettingFlow(userId: Int): Flow<Setting> {
    return localSettingDao.querySettingByUserIdFlow(userId).map { it.toExternal() }
  }

  override suspend fun loadUserSetting(userId: Int): Setting? {
    return localSettingDao.querySettingByUserId(userId)?.toExternal()
  }

  override suspend fun updateAlarmOnSetting(userId: Int, alarmOn: Boolean): String {
    var aaa = localSettingDao.updateAlarmOnQuery(userId, alarmOn)
    return "Done"
  }

  override suspend fun updateAlarmTimeSetting(userId: Int, alarmTime: LocalTime): String {
    var aaa = localSettingDao.updateAlarmTimeQuery(userId, alarmTime)
    return "Done"
  }

  override suspend fun updateBedSetting(userId: Int, bedOn: Boolean): String {
    var aaa = localSettingDao.updateBedSettingQuery(userId, bedOn)
    return "Done"
  }

  override suspend fun updateBedTimeSetting(userId: Int, bedTime: LocalTime): String {
    var aaa = localSettingDao.updateBedTimeQuery(userId, bedTime)
    return "Done"
  }

  override suspend fun updateAlarmTypeSetting(userId: Int, alarmSetting: String): String {
    var aaa = localSettingDao.updateAlarmTypeSettingQuery(userId, alarmSetting)
    return "Done"
  }

  suspend fun countSetting(): Int {
    return localSettingDao.countSetting()
  }


}
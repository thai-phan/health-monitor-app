package com.sewon.healthmonitor.data.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.model.Setting
import com.sewon.healthmonitor.data.model.toExternal
import com.sewon.healthmonitor.data.source.local.dao.LocalSettingDao
import com.sewon.healthmonitor.data.repository.repointerface.ISettingRepository
import com.sewon.healthmonitor.data.model.toLocal
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalTime
import java.util.Date
import javax.inject.Inject

class SettingRepository @Inject constructor(
    private val localSettingDao: LocalSettingDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
): ISettingRepository {

    suspend fun addSetting(setting: Setting) {
        return localSettingDao.upsert(setting.toLocal())
    }

    override fun loadUserSetting(userId: Int): Flow<Setting> {
        return localSettingDao.querySettingByUserId(userId).map { it.toExternal() }
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

    suspend fun countSetting(): Flow<Int> {
        return localSettingDao.countSetting()
    }


}
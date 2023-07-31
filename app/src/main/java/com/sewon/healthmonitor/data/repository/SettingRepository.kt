package com.sewon.healthmonitor.data.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.source.model.Setting
import com.sewon.healthmonitor.data.source.model.toExternal
import com.sewon.healthmonitor.data.source.local.dao.LocalSettingDao
import com.sewon.healthmonitor.data.repository.repointerface.ISettingRepository
import com.sewon.healthmonitor.data.source.model.toLocal
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override suspend fun updateUserAlarm(userId: Int, alarmOn: Boolean): String {
        var aaa = localSettingDao.updateAlarmSetting(userId, alarmOn)
        return "Done"
    }

    suspend fun countSetting(): Flow<Int> {
        return localSettingDao.countSetting()
    }


}
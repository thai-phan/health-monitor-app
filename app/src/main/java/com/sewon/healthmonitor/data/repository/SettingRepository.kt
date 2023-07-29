package com.sewon.healthmonitor.data.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.model.Setting
import com.sewon.healthmonitor.data.model.toExternal
import com.sewon.healthmonitor.data.model.toLocal
import com.sewon.healthmonitor.data.source.local.dao.LocalSettingDao
import com.sewon.healthmonitor.data.source.local.entity.LocalSetting
import com.sewon.healthmonitor.data.repository.repointerface.ISettingRepository
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

    override fun loadUserSetting(user_id: Int): Flow<Setting> {
        return localSettingDao.queryUserSetting(user_id).map { it.toExternal() }
    }

    override suspend fun updateUserAlarm(userId: Int, alarmOn: Boolean): String {
        var aaa = localSettingDao.updateAlarmSetting(userId, alarmOn)
        return "Done"
    }


}
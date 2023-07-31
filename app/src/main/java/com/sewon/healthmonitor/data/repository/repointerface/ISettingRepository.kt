package com.sewon.healthmonitor.data.repository.repointerface

import com.sewon.healthmonitor.data.source.model.Setting
import com.sewon.healthmonitor.data.source.local.entity.LocalSetting
import kotlinx.coroutines.flow.Flow


interface ISettingRepository {

    fun loadUserSetting(userId: Int): Flow<Setting>

    suspend fun updateUserAlarm(userId: Int, alarmOn: Boolean): String
}
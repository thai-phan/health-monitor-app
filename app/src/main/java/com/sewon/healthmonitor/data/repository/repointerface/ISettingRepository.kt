package com.sewon.healthmonitor.data.repository.repointerface

import com.sewon.healthmonitor.data.source.model.Setting
import com.sewon.healthmonitor.data.source.local.entity.LocalSetting
import kotlinx.coroutines.flow.Flow
import java.util.Date


interface ISettingRepository {

    fun loadUserSetting(userId: Int): Flow<Setting>

    suspend fun updateAlarmOnSetting(userId: Int, alarmOn: Boolean): String

    suspend fun updateAlarmTimeSetting(userId: Int, alarmTime: Date): String

    suspend fun updateBedSetting(userId: Int, bedOn: Boolean): String

    suspend fun updateBedTimeSetting(userId: Int, bedTime: String): String

    suspend fun updateAlarmTypeSetting(userId: Int, alarmSetting: String): String

}
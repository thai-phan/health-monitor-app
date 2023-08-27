package com.sewon.healthmonitor.data.repository.repointerface

import com.sewon.healthmonitor.data.model.Setting
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime


interface ISettingRepository {

    fun loadUserSetting(userId: Int): Flow<Setting>

    suspend fun updateAlarmOnSetting(userId: Int, alarmOn: Boolean): String

    suspend fun updateAlarmTimeSetting(userId: Int, alarmTime: LocalTime): String

    suspend fun updateBedSetting(userId: Int, bedOn: Boolean): String

    suspend fun updateBedTimeSetting(userId: Int, bedTime: LocalTime): String

    suspend fun updateAlarmTypeSetting(userId: Int, alarmSetting: String): String


}
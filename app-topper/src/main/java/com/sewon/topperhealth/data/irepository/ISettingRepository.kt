package com.sewon.topperhealth.data.irepository

import com.sewon.topperhealth.data.model.Setting
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime


interface ISettingRepository {

  suspend fun addSetting(setting: Setting): Long

  fun flowLoadUserSetting(userId: Int): Flow<Setting>

  suspend fun loadUserSetting(userId: Int): Setting?

  suspend fun updateAlarmOnSetting(userId: Int, alarmOn: Boolean): Int

  suspend fun updateWakeupTimeSetting(userId: Int, wakeupTime: LocalTime): Int

  suspend fun updateBedSetting(userId: Int, bedOn: Boolean): Int

  suspend fun updateSleepTimeSetting(userId: Int, sleepTime: LocalTime): Int

  suspend fun updateAlarmTypeSetting(userId: Int, alarmSetting: String): Int
}
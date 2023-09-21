package com.sewon.healthmonitor.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sewon.healthmonitor.data.source.local.entity.LocalSetting
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime
import java.util.Date


@Dao
interface LocalSettingDao {
    @Query("SELECT * FROM setting")
    fun getAllSetting(): Flow<List<LocalSetting>>

    @Query("SELECT * FROM setting WHERE user_id = :userid")
    fun querySettingByUserId(userid: Int): Flow<LocalSetting>

    @Query("UPDATE setting SET alarm_on = :alarmOn WHERE user_id = :userid")
    suspend fun updateAlarmOnQuery(userid: Int, alarmOn: Boolean)

    @Query("UPDATE setting SET alarm_time = :alarmTime WHERE user_id = :userid")
    suspend fun updateAlarmTimeQuery(userid: Int, alarmTime: LocalTime)

    @Query("UPDATE setting SET bed_on = :bedOn WHERE user_id = :userid")
    suspend fun updateBedSettingQuery(userid: Int, bedOn: Boolean)

    @Query("UPDATE setting SET bed_time = :bedTime WHERE user_id = :userid")
    suspend fun updateBedTimeQuery(userid: Int, bedTime: LocalTime)

    @Query("UPDATE setting SET alarm_setting = :alarmSetting WHERE user_id = :userid")
    suspend fun updateAlarmTypeSettingQuery(userid: Int, alarmSetting: String)

    @Query("SELECT count(user_id) FROM setting")
    fun countSetting(): Flow<Int>

    @Upsert
    suspend fun upsert(localSetting: LocalSetting)

}
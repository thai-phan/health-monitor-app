package com.sewon.healthmonitor.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sewon.healthmonitor.data.source.local.entity.LocalSetting
import kotlinx.coroutines.flow.Flow


@Dao
interface LocalSettingDao {
    @Query("SELECT * FROM setting")
    fun getAllSetting(): Flow<List<LocalSetting>>

    @Query("SELECT * FROM setting WHERE user_id = :userid")
    fun querySettingByUserId(userid: Int): Flow<LocalSetting>

    @Query("UPDATE setting SET alarm_on = :alarmOn WHERE user_id = :userid")
    suspend fun updateAlarmSetting(userid: Int, alarmOn: Boolean)

    @Query("SELECT count(user_id) FROM setting")
    fun countSetting(): Flow<Int>

    @Upsert
    suspend fun upsert(localSetting: LocalSetting)

}
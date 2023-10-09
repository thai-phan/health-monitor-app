package com.sewon.healthmonitor.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sewon.healthmonitor.data.source.local.entity.LocalSetting
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime


@Dao
interface LocalSettingDao {
  @Query("SELECT * FROM setting")
  fun getAllSetting(): Flow<List<LocalSetting>>

  @Query("SELECT * FROM setting WHERE user_id = :userid")
  fun querySettingByUserIdFlow(userid: Int): Flow<LocalSetting>

  @Query("SELECT * FROM setting WHERE user_id = :userid")
  suspend fun querySettingByUserId(userid: Int): LocalSetting

  @Query("SELECT count(user_id) FROM setting")
  suspend fun countSetting(): Int

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


  @Upsert
  suspend fun upsert(localSetting: LocalSetting)


}
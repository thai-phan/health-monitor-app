package com.sewon.topperhealth.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sewon.topperhealth.data.source.local.entity.LocalSetting
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime


@Dao
interface LocalSettingDao {
  @Query("SELECT * FROM setting")
  fun getAllSetting(): Flow<List<LocalSetting>>

  @Query("SELECT * FROM setting WHERE user_id = :userid")
  fun querySettingByUserIdFlow(userid: Int): Flow<LocalSetting>

  @Query("SELECT * FROM setting WHERE user_id = :userid")
  suspend fun querySettingByUserId(userid: Int): LocalSetting?

  @Query("SELECT count(user_id) FROM setting")
  suspend fun countSetting(): Int

  @Query("UPDATE setting SET alarm_on = :alarmOn WHERE user_id = :userid")
  suspend fun queryUpdateAlarmOnQuery(userid: Int, alarmOn: Boolean): Int

  @Query("UPDATE setting SET wakeup_time = :wakeupTime WHERE user_id = :userid")
  suspend fun queryUpdateWakeupTimeQuery(userid: Int, wakeupTime: LocalTime): Int

  @Query("UPDATE setting SET bed_on = :bedOn WHERE user_id = :userid")
  suspend fun queryUpdateBedSettingQuery(userid: Int, bedOn: Boolean): Int

  @Query("UPDATE setting SET sleep_time = :sleepTime WHERE user_id = :userid")
  suspend fun queryUpdateSleepTimeQuery(userid: Int, sleepTime: LocalTime): Int

  @Query("UPDATE setting SET alarm_behavior = :alarmBehavior WHERE user_id = :userid")
  suspend fun queryUpdateAlarmTypeSettingQuery(userid: Int, alarmBehavior: String): Int

  @Query(
    "UPDATE setting SET relation1 = :relation1, contact1 = :contact1, " +
        "relation2 = :relation2, contact2 = :contact2, " +
        "relation3 = :relation3, contact3 = :contact3 " +
        "WHERE user_id = :userid"
  )
  suspend fun queryUpdateRecipient(
    userid: Int,
    relation1: String, contact1: String,
    relation2: String, contact2: String,
    relation3: String, contact3: String
  ): Int


  @Insert
  suspend fun insert(localSetting: LocalSetting): Long
}
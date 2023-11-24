package com.sewon.topperhealth.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.sewon.topperhealth.data.source.local.entity.LocalSleepSession
import java.util.Date

@Dao
interface LocalSessionDao {
  @Query("SELECT * FROM sleep_session")
  suspend fun getAllSession(): List<LocalSleepSession>

  @Query("SELECT count(session_id) FROM sleep_session")
  suspend fun countSession(): Int

  @Query(
    "UPDATE sleep_session SET " +
        "ref_hrv = :refHRV, " +
        "ref_hr = :refHR, " +
        "ref_br = :refBR  " +
        "WHERE session_id = :sessionId"
  )
  suspend fun queryUpdateSessionRefValue(
    sessionId: Int,
    refHRV: Double,
    refHR: Double,
    refBR: Double
  )

  @Query(
    "UPDATE sleep_session SET " +
        "actual_end_time = :endTime " +
        "WHERE session_id = :sessionId"
  )
  suspend fun queryUpdateSessionEndTime(sessionId: Int, endTime: Date)

  @Query(
    "UPDATE sleep_session SET " +
        "assessment = :assessment " +
        "WHERE session_id = :sessionId"
  )
  suspend fun queryUpdateSessionAssessment(sessionId: Int, assessment: String)

  @Query(
    "UPDATE sleep_session SET " +
        "rating = :rating, " +
        "memo = :memo " +
        "WHERE session_id = :sessionId"
  )
  suspend fun queryUpdateSessionQualityMemo(sessionId: Int, rating: Int, memo: String)

  @Query("SELECT * FROM sleep_session WHERE session_id = :sessionId")
  suspend fun loadById(sessionId: Int): LocalSleepSession


  @Query("DELETE FROM sleep_session")
  suspend fun queryDeleteAll(): Int


  @Insert
  fun insertAll(vararg localSleepSession: LocalSleepSession)

  @Insert
  suspend fun insert(localSleepSession: LocalSleepSession): Long

  @Upsert
  suspend fun upsert(localSleepSession: LocalSleepSession): Long

  @Delete
  fun delete(localSleepSession: LocalSleepSession)

}
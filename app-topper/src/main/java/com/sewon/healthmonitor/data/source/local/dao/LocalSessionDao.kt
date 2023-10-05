package com.sewon.healthmonitor.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.sewon.healthmonitor.data.source.local.entity.LocalSleepSession
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalSessionDao {
  @Query("SELECT * FROM sleep_session")
  fun getAllSession(): Flow<List<LocalSleepSession>>

  @Query("SELECT count(session_id) FROM sleep_session")
  fun countSession(): Int

  @Query(
    "UPDATE sleep_session SET " +
        "ref_hrv = :refHRV, " +
        "ref_hr = :refHR, " +
        "ref_br = :refBR  " +
        "WHERE session_id = :sessionId"
  )
  fun queryUpdateSessionRefValue(refHRV: Double, refHR: Double, refBR: Double, sessionId: Int)

  @Query("SELECT * FROM sleep_session WHERE session_id = :sessionId")
  suspend fun loadById(sessionId: Int): LocalSleepSession?

  @Update
  suspend fun updateSession(localSleepSession: LocalSleepSession)


  @Insert
  fun insertAll(vararg localSleepSession: LocalSleepSession)

  @Insert
  suspend fun insert(localSleepSession: LocalSleepSession): Long

  @Upsert
  suspend fun upsert(localSleepSession: LocalSleepSession): Long

  @Delete
  fun delete(localSleepSession: LocalSleepSession)

}
package com.sewon.healthmonitor.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.sewon.healthmonitor.data.source.local.entity.LocalSession
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalSessionDao {
  @Query("SELECT * FROM session")
  fun getAll(): Flow<List<LocalSession>>

  @Query("SELECT * FROM session WHERE session_id = :sessionId")
  fun loadById(sessionId: Int): LocalSession

  @Query("SELECT count(session_id) FROM session")
  fun countSession(): Flow<Int>

  @Update
  suspend fun updateSession(localSession: LocalSession)

  @Insert
  suspend fun insert(localSession: LocalSession)

  @Upsert
  suspend fun upsert(localSession: LocalSession)

  @Insert
  fun insertAll(vararg localSession: LocalSession)

  @Delete
  fun delete(localSession: LocalSession)

}
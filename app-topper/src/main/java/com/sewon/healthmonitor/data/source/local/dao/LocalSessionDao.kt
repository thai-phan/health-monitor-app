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
  fun getAllSession(): Flow<List<LocalSession>>

  @Query("SELECT count(session_id) FROM session")
  fun countSession(): Flow<Int>


  @Query("SELECT * FROM session WHERE session_id = :sessionId")
  suspend fun loadById(sessionId: Int): LocalSession

  @Update
  suspend fun updateSession(localSession: LocalSession)


  @Insert
  fun insertAll(vararg localSession: LocalSession)

  @Insert
  suspend fun insert(localSession: LocalSession)

  @Upsert
  suspend fun upsert(localSession: LocalSession)

  @Delete
  fun delete(localSession: LocalSession)

}
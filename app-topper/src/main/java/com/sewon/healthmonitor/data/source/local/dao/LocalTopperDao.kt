package com.sewon.healthmonitor.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.sewon.healthmonitor.data.source.local.entity.LocalTopper

@Dao
interface LocalTopperDao {

  @Query("SELECT * FROM topper where session_id = :sessionId")
  fun queryAllDataFromSession(sessionId: Int): List<LocalTopper>

  @Query("SELECT count(br) FROM topper")
  fun queryCountData(): Int


  @Insert
  suspend fun insertAll(vararg localTopper: LocalTopper)

  @Delete
  suspend fun delete(user: LocalTopper)

  @Insert
  suspend fun insert(localTopper: LocalTopper)

  @Upsert
  suspend fun upsert(localTopper: LocalTopper)

  @Upsert
  suspend fun upsertAll(localToppers: List<LocalTopper>)

}
package com.sewon.healthmonitor.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.sewon.healthmonitor.data.source.local.entity.LocalRadar
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalRadarDao {
  @Query("SELECT * FROM radar")
  fun getAllTopper(): Flow<List<LocalRadar>>

  @Query("SELECT count(rb) FROM radar")
  fun countTopper(): Flow<Int>

  @Insert
  suspend fun insertAll(vararg localRadar: LocalRadar)

  @Delete
  suspend fun delete(user: LocalRadar)

  @Insert
  fun insert(localRadar: LocalRadar)

  @Upsert
  suspend fun upsert(localRadar: LocalRadar)

  @Upsert
  suspend fun upsertAll(localRadars: List<LocalRadar>)

}
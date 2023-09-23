package com.sewon.healthmonitor.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.sewon.healthmonitor.data.source.local.entity.LocalSensor
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalSensorDao {
  @Query("SELECT * FROM sensor")
  fun getAllTopper(): Flow<List<LocalSensor>>

  @Query("SELECT count(br) FROM sensor")
  fun countTopper(): Flow<Int>

  @Insert
  suspend fun insertAll(vararg localSensor: LocalSensor)

  @Delete
  suspend fun delete(user: LocalSensor)

  @Insert
  fun insert(localSensor: LocalSensor)

  @Upsert
  suspend fun upsert(localSensor: LocalSensor)

  @Upsert
  suspend fun upsertAll(localSensors: List<LocalSensor>)

}
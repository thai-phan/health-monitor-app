package com.sewon.healthmonitor.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.sewon.healthmonitor.data.source.local.entity.LocalSensor

@Dao
interface LocalSensorDao {

  @Query("SELECT * FROM sensor where session_id = :sessionId")
  fun queryAllDataFromSession(sessionId: Int): List<LocalSensor>

  @Query("SELECT count(br) FROM sensor")
  fun queryCountData(): Int


  @Insert
  suspend fun insertAll(vararg localSensor: LocalSensor)

  @Delete
  suspend fun delete(user: LocalSensor)

  @Insert
  suspend fun insert(localSensor: LocalSensor)

  @Upsert
  suspend fun upsert(localSensor: LocalSensor)

  @Upsert
  suspend fun upsertAll(localSensors: List<LocalSensor>)

}
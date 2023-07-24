package com.sewon.healthmonitor.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.sewon.healthmonitor.data.entity.Radar
import kotlinx.coroutines.flow.Flow

@Dao
interface RadarDao {
    @Query("SELECT * FROM radar")
    fun getAllTopper(): Flow<List<Radar>>

    @Query("SELECT count(rb) FROM radar")
    fun countTopper():  Flow<Int>

    @Insert
    suspend fun insertAll(vararg radar: Radar)

    @Delete
    suspend fun delete(user: Radar)

    @Upsert
    suspend fun upsert(radar: Radar)

    @Upsert
    suspend fun upsertAll(radars: List<Radar>)

}
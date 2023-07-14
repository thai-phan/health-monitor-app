package com.sewon.healthmonitor.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.Query
import androidx.room.Upsert
import com.sewon.healthmonitor.data.entity.Topper
import kotlinx.coroutines.flow.Flow

@Dao
interface TopperDao {
    @Query("SELECT * FROM topper")
    fun getAllTopper(): Flow<List<Topper>>

    @Query("SELECT count(rb) FROM topper")
    fun countTopper():  Flow<Int>

    @Insert
    suspend fun insertAll(vararg topper: Topper)

    @Delete
    suspend fun delete(user: Topper)

    @Upsert
    suspend fun upsert(topper: Topper)

    @Upsert
    suspend fun upsertAll(toppers: List<Topper>)

}
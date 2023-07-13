package com.sewon.healthmonitor.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sewon.healthmonitor.data.entity.Topper
import kotlinx.coroutines.flow.Flow

@Dao
interface TopperDao {
    @Query("SELECT * FROM topper")
    fun getAll(): Flow<List<Topper>>

//    @Query("SELECT * FROM topper WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<Topper>

    @Insert
    fun insertAll(vararg topper: Topper)

    @Delete
    fun delete(user: Topper)

}
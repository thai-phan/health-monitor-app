package com.sewon.healthmonitor.data.repository

import com.sewon.healthmonitor.data.entity.Topper
import kotlinx.coroutines.flow.Flow


interface ITopperRepository {

    fun getTopper(): Flow<List<Topper>>

    fun getCountTopper(): Flow<Int>

    suspend fun createTopper(topper: Topper): String
}
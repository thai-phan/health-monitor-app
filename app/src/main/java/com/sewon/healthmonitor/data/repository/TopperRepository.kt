package com.sewon.healthmonitor.data.repository

import com.sewon.healthmonitor.data.entity.Topper
import kotlinx.coroutines.flow.Flow

interface TopperRepository {

    fun getTopper(): Flow<List<Topper>>

    suspend fun createTopper(topper: Topper): String
}
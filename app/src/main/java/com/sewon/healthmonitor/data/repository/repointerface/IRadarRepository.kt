package com.sewon.healthmonitor.data.repository.repointerface

import com.sewon.healthmonitor.data.entity.Radar
import kotlinx.coroutines.flow.Flow


interface IRadarRepository {

    fun getTopper(): Flow<List<Radar>>

    fun getCountTopper(): Flow<Int>

    suspend fun createTopper(radar: Radar): String
}
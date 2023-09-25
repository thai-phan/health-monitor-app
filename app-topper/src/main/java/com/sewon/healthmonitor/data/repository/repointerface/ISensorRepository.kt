package com.sewon.healthmonitor.data.repository.repointerface

import com.sewon.healthmonitor.data.source.local.entity.LocalSensor
import kotlinx.coroutines.flow.Flow


interface ISensorRepository {

  fun getTopper(): Flow<List<LocalSensor>>

  fun getCountTopper(): Flow<Int>

  suspend fun createTopper(localSensor: LocalSensor): String
}
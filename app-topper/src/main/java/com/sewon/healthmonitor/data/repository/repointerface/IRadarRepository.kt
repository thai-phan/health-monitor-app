package com.sewon.healthmonitor.data.repository.repointerface

import com.sewon.healthmonitor.data.source.local.entity.LocalSensor
import kotlinx.coroutines.flow.Flow


interface IRadarRepository {

  fun getTopper(): Flow<List<LocalSensor>>

  fun getCountTopper(): Flow<Int>

  fun createTopper(localSensor: LocalSensor): String
}
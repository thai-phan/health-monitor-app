package com.sewon.healthmonitor.data.repository.repointerface

import com.sewon.healthmonitor.data.source.local.entity.LocalRadar
import kotlinx.coroutines.flow.Flow


interface IRadarRepository {

  fun getTopper(): Flow<List<LocalRadar>>

  fun getCountTopper(): Flow<Int>

  suspend fun createTopper(localRadar: LocalRadar): String
}
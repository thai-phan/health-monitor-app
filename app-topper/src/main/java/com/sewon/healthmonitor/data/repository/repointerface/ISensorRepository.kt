package com.sewon.healthmonitor.data.repository.repointerface

import com.sewon.healthmonitor.data.source.local.entity.LocalSensor
import kotlinx.coroutines.flow.Flow


interface ISensorRepository {


  fun getDataFromSession(sessionId: Int): List<LocalSensor>

  fun getCountTopper(): Int

  suspend fun createTopper(localSensor: LocalSensor): String
}
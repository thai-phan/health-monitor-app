package com.sewon.healthmonitor.data.repository.repointerface

import com.sewon.healthmonitor.data.source.local.entity.LocalSensor


interface ISensorRepository {


  fun getAllDataFromSession(sessionId: Int): List<LocalSensor>

  fun getDataCount(): Int

  suspend fun addSensorData(localSensor: LocalSensor): String
}
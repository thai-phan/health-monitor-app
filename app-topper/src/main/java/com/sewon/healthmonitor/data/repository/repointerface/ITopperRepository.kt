package com.sewon.healthmonitor.data.repository.repointerface

import com.sewon.healthmonitor.data.source.local.entity.LocalTopper


interface ITopperRepository {

  suspend fun getAllDataFromSession(sessionId: Int): List<LocalTopper>

  suspend fun getDataCount(): Int

  suspend fun insertNewTopperData(localTopper: LocalTopper): String
}
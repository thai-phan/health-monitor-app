package com.sewon.topperhealth.data.irepository

import com.sewon.topperhealth.data.source.local.entity.LocalTopper


interface ITopperRepository {

  suspend fun getAllDataFromSession(sessionId: Int): List<LocalTopper>

  suspend fun getDataCount(): Int

  suspend fun insertNewTopperData(localTopper: LocalTopper): String

  suspend fun deleteAll(): Int
}
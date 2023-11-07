package com.sewon.topperhealth.data.source.local.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.topperhealth.data.source.local.dao.LocalTopperDao
import com.sewon.topperhealth.data.source.local.entity.LocalTopper
import com.sewon.topperhealth.data.irepository.ITopperRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class TopperRepository @Inject constructor(
  private val localTopperDao: LocalTopperDao,
  @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
  @ApplicationScope private val scope: CoroutineScope,
) : ITopperRepository {


  override suspend fun getAllDataFromSession(sessionId: Int): List<LocalTopper> {
    return localTopperDao.queryAllDataFromSession(sessionId)
  }


  override suspend fun getDataCount(): Int {
    return localTopperDao.queryCountData()
  }
//     Count number record


  override suspend fun insertNewTopperData(localTopper: LocalTopper): String {
    var aaa = localTopperDao.insert(localTopper)
    return "true"
  }

  override suspend fun deleteAll(): Int {
    return localTopperDao.queryDeleteAll()
  }
}
package com.sewon.healthmonitor.data.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.source.local.dao.LocalTopperDao
import com.sewon.healthmonitor.data.source.local.entity.LocalTopper
import com.sewon.healthmonitor.data.repository.repointerface.ITopperRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class TopperRepository @Inject constructor(
  private val LocalTopperDao: LocalTopperDao,
  @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
  @ApplicationScope private val scope: CoroutineScope,
) : ITopperRepository {


  override fun getAllDataFromSession(sessionId: Int): List<LocalTopper> {
    return LocalTopperDao.queryAllDataFromSession(sessionId)
  }


  override fun getDataCount(): Int {
    return LocalTopperDao.queryCountData()
  }
//     Count number record


  override suspend fun insertNewTopperData(localTopper: LocalTopper): String {
    var aaa = LocalTopperDao.insert(localTopper)
    return "true"
  }

}
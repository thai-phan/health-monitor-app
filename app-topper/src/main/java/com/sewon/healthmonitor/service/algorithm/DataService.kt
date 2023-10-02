package com.sewon.healthmonitor.service.algorithm

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.sewon.healthmonitor.data.model.toLocal
import com.sewon.healthmonitor.data.repository.repointerface.ISensorRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DataService : Service() {

  private val job = SupervisorJob()
  private val scope = CoroutineScope(Dispatchers.IO + job)

  @Inject
  lateinit var sensorRepository: ISensorRepository

  fun addData() {
    scope.launch {
      var aaa = sensorRepository.getAllDataFromSession(1)
    }
  }

  override fun onBind(intent: Intent?): IBinder? {
    TODO("Not yet implemented")
  }


}
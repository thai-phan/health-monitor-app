package com.sewon.healthmonitor.screen.report.component.c

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.data.repository.repointerface.ISensorRepository
import com.sewon.healthmonitor.data.repository.repointerface.ISessionRepository
import com.sewon.healthmonitor.data.source.local.entity.LocalSession
import com.sewon.healthmonitor.service.algorithm.sleep.AlgorithmReport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject


data class UiState(
  val sleepTime: Float = 0.0f,
  val sleepEfficiency: Float = 0.0f,
  val sleepLatency: Float = 0.0f,
  val wakeupOnSleep: Float = 0.0f,
)

@HiltViewModel
class SleepDetailViewModel @Inject constructor(
  private val sessionRepository: ISessionRepository,
  private val sensorRepository: ISensorRepository,

  ) : ViewModel() {

  private val _uiState = MutableStateFlow(UiState())
  val uiState: StateFlow<UiState> = _uiState.asStateFlow()

  private fun initFirstSession() {
    viewModelScope.launch {

    }
  }

  init {
    initFirstSession()
  }

  fun loadData() {
    getMeanHR()
//    getMeanBR()
//    getSDRP()
//    getRMSSD()
//    getRPITriangular()
//    getLowFrequence()
//    getHighFrequence()
//    getLFRatio()
  }


  fun getMeanHR() = viewModelScope.launch {
//    TODO: Get sleep score by 5 day

    var aaa = sensorRepository.getDataFromSession(1)


    val session: LocalSession = sessionRepository.getSessionById(1)
    val startTime: LocalTime = session.startTime
    val endTime: LocalTime = session.endTime

    val sleepTime = startTime.until(endTime, ChronoUnit.HOURS).toFloat()
    _uiState.update {
      it.copy(sleepTime = sleepTime)
    }
  }

  fun getMeanBR() = viewModelScope.launch {
    //    TODO: query all data -> Sleep Stage
    val session: LocalSession = sessionRepository.getSessionById(1)
    AlgorithmReport.processReport(session.refHRV, session.refHR, session.refBR)
    val sleepEfficiency = session.rating.toFloat()
    _uiState.update {
      it.copy(sleepEfficiency = sleepEfficiency)
    }
  }

  fun getSDRP() = viewModelScope.launch {
//    TODO: query all data -> Sleep RPI
    val sleepLatency = 0.0f

    _uiState.update {
      it.copy(sleepLatency = sleepLatency)
    }
  }

  fun getRMSSD() = viewModelScope.launch {
//    TODO: query all data -> Sleep RPI
    val sleepLatency = 0.0f

    _uiState.update {
      it.copy(sleepLatency = sleepLatency)
    }
  }

  fun getRPITriangular() = viewModelScope.launch {
//    TODO: query all data -> Sleep RPI
    val sleepLatency = 0.0f

    _uiState.update {
      it.copy(sleepLatency = sleepLatency)
    }
  }

  fun getLowFrequence() = viewModelScope.launch {
//    TODO: query all data -> Sleep RPI
    val sleepLatency = 0.0f

    _uiState.update {
      it.copy(sleepLatency = sleepLatency)
    }
  }

  fun getHighFrequence() = viewModelScope.launch {
//    TODO: query all data -> Sleep RPI
    val sleepLatency = 0.0f

    _uiState.update {
      it.copy(sleepLatency = sleepLatency)
    }
  }

  fun getLFRatio() = viewModelScope.launch {
//    TODO: query all data -> Sleep RPI
    val sleepLatency = 0.0f

    _uiState.update {
      it.copy(sleepLatency = sleepLatency)
    }
  }

}

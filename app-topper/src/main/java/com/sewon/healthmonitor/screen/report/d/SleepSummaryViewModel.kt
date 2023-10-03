package com.sewon.healthmonitor.screen.report.d

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.data.repository.repointerface.ISessionRepository
import com.sewon.healthmonitor.data.source.local.entity.LocalSession
import com.sewon.healthmonitor.service.algorithm.sleep.database.ReportAlgorithm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject


data class UiStateD(
  val sleepTime: Float = 0.0f,
  val sleepEfficiency: Float = 0.0f,
  val sleepLatency: Float = 0.0f,
  val wakeupOnSleep: Float = 0.0f,
)

@HiltViewModel
class SleepSummaryViewModel @Inject constructor(
  private val sessionRepository: ISessionRepository,
) : ViewModel() {

  private val _uiStateD = MutableStateFlow(UiStateD())
  val uiStateD: StateFlow<UiStateD> = _uiStateD.asStateFlow()

  private fun initFirstSession() {
    viewModelScope.launch {

    }
  }

  init {
    initFirstSession()
  }

  fun loadData() {
    getSleepScore()
    getSleepStage()
    getSleepRPI()
  }


  fun getSleepScore() = viewModelScope.launch {
//    TODO: Get sleep score by 5 day
    val session: LocalSession = sessionRepository.getSessionById(1)
    val startTime: LocalTime = session.startTime
    val endTime: LocalTime = session.endTime

    val sleepTime = startTime.until(endTime, ChronoUnit.HOURS).toFloat()
    _uiStateD.update {
      it.copy(sleepTime = sleepTime)
    }
  }

  fun getSleepStage() = viewModelScope.launch {
    //    TODO: query all data -> Sleep Stage
    val session: LocalSession = sessionRepository.getSessionById(1)
//    ReportAlgorithm.processReport(session.refHRV, session.refHR, session.refBR)
    val sleepEfficiency = session.rating.toFloat()
    _uiStateD.update {
      it.copy(sleepEfficiency = sleepEfficiency)
    }
  }


  fun getSleepRPI() = viewModelScope.launch {
//    TODO: query all data -> Sleep RPI
    val sleepLatency = 0.0f

    _uiStateD.update {
      it.copy(sleepLatency = sleepLatency)
    }
  }


}

package com.sewon.healthmonitor.screen.report.component.a

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.data.model.Session
import com.sewon.healthmonitor.data.repository.repointerface.ISessionRepository
import com.sewon.healthmonitor.data.repository.repointerface.IUserRepository
import com.sewon.healthmonitor.data.source.local.entity.LocalSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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
class SleepChartViewModel @Inject constructor(
  private val sessionRepository: ISessionRepository,
) : ViewModel() {

  private val _uiState = MutableStateFlow(UiState())
  val uiState: StateFlow<UiState> = _uiState.asStateFlow()

  private fun initFirstSession() {
    viewModelScope.launch {
      if (sessionRepository.countSession().first() == 0) {
        val curTime = LocalTime.now()
        val session = Session(curTime, curTime.plusHours(3))
        sessionRepository.addSession(session)
      }
    }
  }

  init {
    initFirstSession()
  }


  fun getTotalSleepTime() = viewModelScope.launch {
    val session: LocalSession = sessionRepository.getSessionById(1)
    val startTime: LocalTime = session.createdAt
    val endTime: LocalTime = session.finishedAt

    val sleepTime = startTime.until(endTime, ChronoUnit.HOURS).toFloat()
    _uiState.update {
      it.copy(sleepTime = sleepTime)
    }
  }

  fun getSleepEfficiency() = viewModelScope.launch {
    // TODO:
    val sleepEfficiency = 0.0f
    _uiState.update {
      it.copy(sleepEfficiency = sleepEfficiency)
    }
  }


  fun getSleepLatency() = viewModelScope.launch {
//    TODO:
    val sleepLatency = 0.0f
    _uiState.update {
      it.copy(sleepLatency = sleepLatency)
    }
  }

  fun getWakeOnSleep() = viewModelScope.launch {
//    TODO:
    val wakeupOnSleep = 0.0f
    _uiState.update {
      it.copy(wakeupOnSleep = wakeupOnSleep)
    }
  }

}

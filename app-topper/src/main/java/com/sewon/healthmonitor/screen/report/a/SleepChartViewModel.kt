package com.sewon.healthmonitor.screen.report.a

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.data.model.Session
import com.sewon.healthmonitor.data.repository.repointerface.ISessionRepository
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


data class UiStateA(
  val sleepTime: Float = 0.0f,
  val sleepEfficiency: Float = 0.0f,
  val sleepLatency: Float = 0.0f,
  val wakeupOnSleep: Float = 0.0f,
)

@HiltViewModel
class SleepChartViewModel @Inject constructor(
  private val sessionRepository: ISessionRepository,
) : ViewModel() {

  private val _uiStateA = MutableStateFlow(UiStateA())
  val uiStateA: StateFlow<UiStateA> = _uiStateA.asStateFlow()

  private fun initFirstSession() {
    viewModelScope.launch {
      if (sessionRepository.countSession().first() == 0) {
        val curTime = LocalTime.now()
        val session = Session(
          0.0, 0.0, 0.0,
          0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
          50, 10,
          curTime, curTime, curTime.plusHours(3)
        )
        sessionRepository.addSession(session)
      }
    }
  }

  init {
    initFirstSession()
  }

  fun loadData() {
    getTotalSleepTime()
    getSleepEfficiency()
    getSleepLatency()
    getWakeOnSleep()
  }

  fun getTotalSleepTime() = viewModelScope.launch {
    val session: LocalSession = sessionRepository.getSessionById(1)
    val startTime: LocalTime = session.startTime
    val wakeUpTime: LocalTime = session.endTime

    val sleepTime = startTime.until(wakeUpTime, ChronoUnit.HOURS).toFloat()
    _uiStateA.update {
      it.copy(sleepTime = sleepTime)
    }
  }

  fun getSleepEfficiency() = viewModelScope.launch {
    val session: LocalSession = sessionRepository.getSessionById(1)
    val sleepEfficiency = session.rating.toFloat()

    _uiStateA.update {
      it.copy(sleepEfficiency = sleepEfficiency)
    }
  }


  fun getSleepLatency() = viewModelScope.launch {
    val session: LocalSession = sessionRepository.getSessionById(1)
    val startTime: LocalTime = session.startTime
    val sleepTime: LocalTime = session.sleepTime
    val sleepLatency = startTime.until(sleepTime, ChronoUnit.MINUTES).toFloat()

    _uiStateA.update {
      it.copy(sleepLatency = sleepLatency)
    }
  }

  fun getWakeOnSleep() = viewModelScope.launch {
    val session: LocalSession = sessionRepository.getSessionById(1)
    val wakeupOnSleep = session.wakeUpCount.toFloat()
    _uiStateA.update {
      it.copy(wakeupOnSleep = wakeupOnSleep)
    }
  }
}

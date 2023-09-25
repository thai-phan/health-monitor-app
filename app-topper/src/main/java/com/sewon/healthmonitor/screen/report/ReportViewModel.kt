package com.sewon.healthmonitor.screen.report


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.data.model.Session
import com.sewon.healthmonitor.data.repository.repointerface.ISessionRepository
import com.sewon.healthmonitor.data.repository.repointerface.IUserRepository
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
  val sleepTime: Long = 0L,
  val status2: String = "",
  val status3: String = "",
  val description: String = "",
  val isTaskCompleted: Boolean = false,
  val isLoading: Boolean = false,
  val userMessage: Int? = null,
  val isTaskSaved: Boolean = false
)

@HiltViewModel
class ReportViewModel @Inject constructor(
  private val userRepository: IUserRepository,
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
    var session = sessionRepository.getSessionById(1)
    var startTime = session.createdAt
    var endTime = session.finishedAt

    var sleepTime = startTime.until(endTime, ChronoUnit.HOURS)
    _uiState.update {
      it.copy(sleepTime = sleepTime)
    }
  }

}



package com.sewon.topperhealth.screen.activity

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.topperhealth.api.HttpService
import com.sewon.topperhealth.api.ServerService
import com.sewon.topperhealth.screen.a0common.timepicker.TimeRangePicker
import com.sewon.topperhealth.data.irepository.ISessionRepository
import com.sewon.topperhealth.data.irepository.ISettingRepository
import com.sewon.topperhealth.data.irepository.ITopperRepository
import com.sewon.topperhealth.data.irepository.IUserRepository
import com.sewon.topperhealth.data.model.SleepSession
import com.sewon.topperhealth.service.bluetooth.LowEnergyService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date
import javax.inject.Inject


data class UiState(
  val sessionId: Int = 0,
  val startTime: TimeRangePicker.Time = TimeRangePicker.Time(22, 0),
  val endTime: TimeRangePicker.Time = TimeRangePicker.Time(7, 0),
  val status1: String = "",
  val status2: String = "",
  val status3: String = "",
  val description: String = "",
  val isTaskCompleted: Boolean = false,
  val isLoading: Boolean = false,
  val userMessage: Int? = null,
  val isTaskSaved: Boolean = false,
)


@HiltViewModel
class ActivityViewModel @Inject constructor(
  private val topperRepository: ITopperRepository,
  private val sessionRepository: ISessionRepository,
  private val settingRepository: ISettingRepository,
  private val userRepository: IUserRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow(UiState())
  val uiState: StateFlow<UiState> = _uiState.asStateFlow()

  private var thisSessionId = 0

  private fun loadData() = viewModelScope.launch {
    val user = settingRepository.loadUserSetting(0)
    if (user != null) {
      val startTime = TimeRangePicker.Time(user.sleepTime.hour, user.sleepTime.minute)
      val endTime = TimeRangePicker.Time(user.wakeupTime.hour, user.wakeupTime.minute)
      _uiState.update {
        it.copy(
          startTime = startTime,
          endTime = endTime,
        )
      }
    }
  }

  fun updateStartTime(startTime: TimeRangePicker.Time) {
    _uiState.update {
      it.copy(
        startTime = startTime,
      )
    }
  }

  fun updateEndTime(endTime: TimeRangePicker.Time) {
    _uiState.update {
      it.copy(
        endTime = endTime,
      )
    }
  }

  init {
    loadData()
  }


  fun createSession(pickerStartTime: Calendar, pickerEndTime: Calendar) = viewModelScope.launch {
    val currentTimeMillis = Date()

    val sleepSession = SleepSession(
      0.0, 0.0, 0.0, false,
      0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
      0, 0,
      pickerStartTime.time,
      pickerEndTime.time,
      currentTimeMillis,
      currentTimeMillis,
      currentTimeMillis,
      "", ""
    )
    val sessionId: Int = sessionRepository.createNewSession(sleepSession).toInt()
    thisSessionId = sessionId
    LowEnergyService.sessionId = sessionId
    LowEnergyService.pickerEndTime = pickerEndTime.time
  }

  fun updateCurrentSessionEndTime() = viewModelScope.launch {
    val actualEndTime = Date()
    sessionRepository.updateSessionEndTime(thisSessionId, actualEndTime)
    Timber.tag("Timber").d("updateSessionRefValue")
  }

  fun saveAssessment(assessment: String) = viewModelScope.launch {
    sessionRepository.updateSessionAssessment(thisSessionId, assessment)
  }

  fun saveQuality(rating: Int, memo: String) = viewModelScope.launch {
    sessionRepository.updateSessionQualityMemo(thisSessionId, rating, memo)
  }


  val exceptionHandler = CoroutineExceptionHandler { _, error ->
    // Do what you want with the error
    Timber.e(error)
  }

  fun queryFromServer() = viewModelScope.launch(exceptionHandler) {
    try {
      val quotesApi = ServerService.create().testServer()
      quotesApi.let {
        Timber.v(it.totalCount.toString())
      }
      _uiState.update {
        it.copy(status3 = quotesApi.count.toString())
      }
    } catch (error: Error) {
      _uiState.update {
        it.copy(status3 = "Disconnect")
      }
    }

  }

  fun queryFromServerHttp() = viewModelScope.launch {
    try {
      val quotesApi = HttpService.create().testServer()
      quotesApi.let {
        Timber.v(it.data)
      }
      // launching a new coroutine
      _uiState.update {
        it.copy(status3 = quotesApi.data)
      }
    } catch (error: Error) {
      Timber.v("catch")
      _uiState.update {
        it.copy(status3 = "Disconnect")
      }
    }
    println("asdas")
  }


}
package com.sewon.healthmonitor.screen.activity

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.MainActivity
import com.sewon.healthmonitor.api.HttpService
import com.sewon.healthmonitor.api.ServerService
import com.sewon.healthmonitor.common.timepicker.TimeRangePicker
import com.sewon.healthmonitor.data.irepository.ISessionRepository
import com.sewon.healthmonitor.data.irepository.ISettingRepository
import com.sewon.healthmonitor.data.irepository.ITopperRepository
import com.sewon.healthmonitor.data.irepository.IUserRepository
import com.sewon.healthmonitor.data.model.SleepSession
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
  val startTime: TimeRangePicker.Time = TimeRangePicker.Time(1, 0),
  val endTime: TimeRangePicker.Time = TimeRangePicker.Time(2, 0),
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

  fun loadData() = viewModelScope.launch {
    val user = settingRepository.loadUserSetting(0)
    val startTime = TimeRangePicker.Time(user.bedTime.hour, user.bedTime.minute)
    val endTime = TimeRangePicker.Time(user.alarmTime.hour, user.alarmTime.minute)
    _uiState.update {
      it.copy(
        startTime = startTime,
        endTime = endTime,
      )
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
    val actualStartTime = Date()
    val sleepTime = Date()

    val sleepSession = SleepSession(
      0.0, 0.0, 0.0, false,
      0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
      90, 10,
      pickerStartTime.time,
      pickerEndTime.time,
      actualStartTime,
      sleepTime,
      sleepTime,
    )
    val sessionId: Int = sessionRepository.createNewSession(sleepSession).toInt()
    thisSessionId = sessionId
    MainActivity.bleHandleService.sessionId = sessionId
    MainActivity.bleHandleService.pickerEndTime = pickerEndTime.time
  }

  fun updateCurrentSessionEndTime() = viewModelScope.launch {
    val actualEndTime = Date()
    sessionRepository.updateSessionEndTime(thisSessionId, actualEndTime)
    Timber.tag("Timber").d("updateSessionRefValue")
  }


  fun createNewTask() = viewModelScope.launch {
//    val localRadar = LocalRadar(0.1f, 0.1f, 0.1f, "X", "X", "X", "X")
//
//    radarRepository.createTopper(localRadar)
    _uiState.update {
      it.copy(status1 = "Ahahahah")
    }
  }

  fun getToppers() = viewModelScope.launch {

//    var aaa = sensorRepository.getAllDataFromSession(1)
//
//    _uiState.update {
//      it.copy(status2 = aaa[0].br.toString())
//    }
  }

  fun getCount() = viewModelScope.launch {

    var bbb = topperRepository.getDataCount()

    _uiState.update {
      it.copy(status3 = bbb.toString())
    }
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
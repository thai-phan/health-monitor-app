package com.sewon.healthmonitor.screen.activity

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.MainActivity
import com.sewon.healthmonitor.api.HttpService
import com.sewon.healthmonitor.api.ServerService
import com.sewon.healthmonitor.common.timepicker.TimeRangePicker
import com.sewon.healthmonitor.data.model.SleepSession
import com.sewon.healthmonitor.data.repository.repointerface.ISessionRepository
import com.sewon.healthmonitor.data.repository.repointerface.ISettingRepository
import com.sewon.healthmonitor.data.repository.repointerface.ITopperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalTime
import java.util.Date
import java.util.Locale
import javax.inject.Inject


data class UiState(
  val status1: String = "",
  val status2: String = "",
  val status3: String = "",
  val description: String = "",
  val isTaskCompleted: Boolean = false,
  val isLoading: Boolean = false,
  val userMessage: Int? = null,
  val isTaskSaved: Boolean = false
)


@HiltViewModel
class ActivityViewModel @Inject constructor(
  private val sensorRepository: ITopperRepository,
  private val sessionRepository: ISessionRepository,
  private val settingRepository: ISettingRepository,
) : ViewModel() {

  private val _uiState = MutableStateFlow(UiState())
  val uiState: StateFlow<UiState> = _uiState.asStateFlow()

  fun createSession(
    startTime: TimeRangePicker.Time,
    endTime: TimeRangePicker.Time,
    duration: TimeRangePicker.TimeDuration
  ) = viewModelScope.launch {
    val startTimeCalendar = GregorianCalendar.getInstance()
    startTimeCalendar.set(GregorianCalendar.HOUR_OF_DAY, startTime.hour)
    startTimeCalendar.set(GregorianCalendar.MINUTE, startTime.minute)

    val endTimeCalendar = startTimeCalendar.clone() as Calendar?
    endTimeCalendar!!.add(GregorianCalendar.HOUR_OF_DAY, duration.hour)
    endTimeCalendar.add(GregorianCalendar.MINUTE, duration.minute)

    val actualStartTime = Date()
    val sleepTime = Date()

    val sleepSession = SleepSession(
      0.0, 0.0, 0.0, false,
      0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
      90, 10,
      startTimeCalendar.time,
      actualStartTime,
      sleepTime,
      endTimeCalendar.time,
    )
    val sessionId: Int = sessionRepository.addSession(sleepSession).toInt()

    MainActivity.bleHandleService.sessionId = sessionId
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

    var bbb = sensorRepository.getDataCount()

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
    // launching a new coroutine

    println("asdas")
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
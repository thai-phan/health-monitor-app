package com.sewon.topperhealth.ui.screen.activity

import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.topperhealth.MainActivity
import com.sewon.topperhealth.data.irepository.ISessionRepository
import com.sewon.topperhealth.data.irepository.ISettingRepository
import com.sewon.topperhealth.data.irepository.ITopperRepository
import com.sewon.topperhealth.data.irepository.IUserRepository
import com.sewon.topperhealth.data.model.SleepSession

import com.sewon.topperhealth.service.algorithm.sleep.realtime.RealtimeHandler
import com.sewon.topperhealth.service.bluetooth.LowEnergyClient
import com.sewon.topperhealth.service.bluetooth.LowEnergyService
import com.sewon.topperhealth.service.bluetooth.util.Connected
import com.sewon.topperhealth.ui.common.timepicker.TimeRangePicker
import dagger.hilt.android.lifecycle.HiltViewModel
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
)

data class DataState(
  var sessionId: Int = 0,
  val assessment: Int = 0,
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
  private val _dataState = MutableStateFlow(DataState())

  val uiState: StateFlow<UiState> = _uiState.asStateFlow()


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

  fun getCalendarFromTimePicker(timePicker: TimeRangePicker.Time): Calendar {
    val calendar = GregorianCalendar.getInstance()
    calendar.set(GregorianCalendar.HOUR_OF_DAY, timePicker.hour)
    calendar.set(GregorianCalendar.MINUTE, timePicker.minute)
    calendar.set(GregorianCalendar.SECOND, 0)
    return calendar
  }

  fun startSession(ref: Int, startTimeCalendar: Calendar, endTimeCalendar: Calendar) {
    MainActivity.lowEnergyService.playSoundSleepInduce()
    RealtimeHandler.resetData(ref)
    LowEnergyClient.isStarted.value = true
    if (endTimeCalendar < GregorianCalendar.getInstance()) {
      endTimeCalendar.add(GregorianCalendar.DAY_OF_MONTH, 1)
    }
    createSession(startTimeCalendar, endTimeCalendar)
  }

  private fun createSession(pickerStartTime: Calendar, pickerEndTime: Calendar) =
    viewModelScope.launch {

      val sleepSession = SleepSession(
        pickerStartTime = pickerStartTime.time,
        pickerEndTime = pickerEndTime.time,
      )

      val sessionId: Int = sessionRepository.createNewSession(sleepSession).toInt()
      _dataState.update {
        it.copy(sessionId = sessionId)
      }
      LowEnergyService.sessionId = sessionId
      LowEnergyService.pickerEndTime = pickerEndTime.time
    }

  fun endSession() = viewModelScope.launch {
    LowEnergyClient.isStarted.value = false
    LowEnergyClient.connected.value = Connected.False

    MainActivity.alarmManager.cancel(MainActivity.alarmPendingIntent)
    MainActivity.lowEnergyService.stopSoundSleepInduce()
    MainActivity.lowEnergyService.disconnectBluetoothGatt()

    updateCurrentSessionEndTime()
  }

  private fun updateCurrentSessionEndTime() = viewModelScope.launch {
    val actualEndTime = Date()
    sessionRepository.updateSessionEndTime(_dataState.value.sessionId, actualEndTime)
    Timber.tag("Timber").d("updateSessionRefValue")
  }

  fun saveAssessment(assessment: Int) = viewModelScope.launch {
    _dataState.update {
      it.copy(
        assessment = assessment,
      )
    }
  }

  fun savePSQI(rating: Int, memo: String) = viewModelScope.launch {
//  Rating
    val scoreRating = 4 - rating

    val session = sessionRepository.getSessionById(_dataState.value.sessionId)
    val startTime: Date = session.actualStartTime
    val wakeUpTime: Date = session.actualEndTime
    val sleepTime: Date = session.fellAsleepTime

//    Sleep latency time (SLT)
    val tenMinute = 10 * 60 * 1000
    val sleepLatency = ((sleepTime.time - startTime.time).toFloat() + tenMinute) / (60 * 1000)
    val scoreSleepLatency = when {
      120 <= sleepLatency -> 3
      60 <= sleepLatency -> 2
      30 <= sleepLatency -> 1
      else -> 0
    }

//    Total sleep time (TST)
    val totalSleepTime = (wakeUpTime.time - startTime.time).toFloat() / (3600 * 1000)

    val scoreTotalSleep = when {
      7 <= totalSleepTime -> 0
      6 <= totalSleepTime -> 1
      5 <= totalSleepTime -> 2
      else -> 3
    }

//    Sleep efficiency
    val wakeupOnSleep = session.wakeUpCount.toFloat() / (20 * 60)
    val realSleepTime = (totalSleepTime * 60 - sleepLatency - wakeupOnSleep) / (totalSleepTime * 60)
    val scoreSleepEfficiency = when {
      0.85 <= realSleepTime -> 0
      0.75 <= realSleepTime -> 1
      0.65 <= realSleepTime -> 2
      else -> 3
    }

//    assessment
    val scoreAssessment = _dataState.value.assessment
//    PSQI
    val score =
      18 - scoreRating - scoreAssessment - scoreTotalSleep - scoreSleepLatency - scoreSleepEfficiency

    sessionRepository.updateSessionQualityMemo(_dataState.value.sessionId, score, memo)
  }
}
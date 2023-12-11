package com.sewon.topperhealth.screen.activity

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.topperhealth.api.OpenAIService
import com.sewon.topperhealth.api.RequestBodySleepAdvice
import com.sewon.topperhealth.api.AdviceMessage
import com.sewon.topperhealth.data.irepository.ISessionRepository
import com.sewon.topperhealth.data.irepository.ISettingRepository
import com.sewon.topperhealth.data.irepository.ITopperRepository
import com.sewon.topperhealth.data.irepository.IUserRepository
import com.sewon.topperhealth.data.model.SleepSession
import com.sewon.topperhealth.screen.a0common.timepicker.TimeRangePicker
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

  fun queryOpenAI() = viewModelScope.launch {
    try {
      val systemMessage = AdviceMessage("system", "You are a helpful assistant.")
      val userMessage =
        AdviceMessage("user", "PSQI 점수가 90점이고, 오후 10시에 수면 시작 오전 7시에 일어났어. 이 수면에 대한 조언을 50자 이내로 해줘.")
      val body = RequestBodySleepAdvice(
        model = "gpt-3.5-turbo",
        messages = arrayListOf(systemMessage, userMessage),
        temperature = 1,
        max_tokens = 256,
        top_p = 1,
        frequency_penalty = 0,
        presence_penalty = 0
      )

      val response = OpenAIService.create().getSleepAdvice(body)

      _uiState.update {
        it.copy(status3 = response.choices[0].message.content)
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
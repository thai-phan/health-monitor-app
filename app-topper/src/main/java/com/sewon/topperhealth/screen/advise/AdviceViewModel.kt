package com.sewon.topperhealth.screen.advise

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date
import javax.inject.Inject


data class UiState(
  val startTime: TimeRangePicker.Time = TimeRangePicker.Time(22, 0),
  val endTime: TimeRangePicker.Time = TimeRangePicker.Time(7, 0),
  var advise: String = "",
)


@HiltViewModel
class AdviceViewModel @Inject constructor(
  private val topperRepository: ITopperRepository,
  private val sessionRepository: ISessionRepository,
  private val settingRepository: ISettingRepository,
  private val userRepository: IUserRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow(UiState())

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

  init {
    loadData()
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
        it.copy(advise = response.choices[0].message.content)
      }
    } catch (error: Error) {
      Timber.v("catch")
//      _uiState.update {
//        it.copy(status3 = "Disconnect")
//      }
    }
    println("asdas")
  }


}
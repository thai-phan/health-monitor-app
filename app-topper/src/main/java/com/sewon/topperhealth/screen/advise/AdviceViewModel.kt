package com.sewon.topperhealth.screen.advise

import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.topperhealth.api.openai.AdviceMessage
import com.sewon.topperhealth.api.openai.ServiceOpenAI
import com.sewon.topperhealth.api.openai.RequestBodySleepAdvice
import com.sewon.topperhealth.data.irepository.ISessionRepository
import com.sewon.topperhealth.data.irepository.ISettingRepository
import com.sewon.topperhealth.data.irepository.ITopperRepository
import com.sewon.topperhealth.data.irepository.IUserRepository
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
  val startTime: Int = 0,
  val endTime: Int = 0,
  val psqi: Int = 18,
  val question: String = "",
  var advise: String = "",
  var isLoading: Boolean = false
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
    val session = sessionRepository.getSessionById(LowEnergyService.sessionId)

    val startTime: Date = session.actualStartTime
    val wakeUpTime: Date = session.actualEndTime

    val startCalendar = GregorianCalendar.getInstance()
    startCalendar.time = startTime
    val startHour = startCalendar.get(Calendar.HOUR)

    val wakeupCalendar = GregorianCalendar.getInstance()
    wakeupCalendar.time = wakeUpTime
    val endHour = wakeupCalendar.get(Calendar.HOUR)

    val message =
      "PSQI 점수가 ${session.rating}점이고, 오후 ${startHour}시에 수면 시작 오전 ${endHour}시에 일어났어. 이 수면에 대한 조언을 50자 이내로 해줘."

    _uiState.update {
      Timber.tag("update").d("update")
      it.copy(
        startTime = startHour,
        endTime = endHour,
        psqi = session.rating,
        question = message
      )
    }
  }

  init {
    loadData()
  }

  fun queryOpenAI(key: String) = viewModelScope.launch {
    Timber.tag("query").d("query")
    _uiState.update {
      it.copy(
        isLoading = true
      )
    }
    try {
      val systemMessage = AdviceMessage("system", "You are a helpful assistant.")

      val userMessage =
        AdviceMessage(
          "user",
          _uiState.value.question
        )
      val body = RequestBodySleepAdvice(
        model = "gpt-3.5-turbo",
        messages = arrayListOf(systemMessage, userMessage),
        temperature = 1,
        max_tokens = 256,
        top_p = 1,
        frequency_penalty = 0,
        presence_penalty = 0
      )
      val response = ServiceOpenAI.create(key).getSleepAdvice(body)

      _uiState.update {
        it.copy(
          advise = response.choices[0].message.content,
          isLoading = false
        )
      }
    } catch (error: Error) {
      Timber.v("catch")
      _uiState.update {
        it.copy(
          advise = "Error",
          isLoading = false
        )
      }
    }
  }
}
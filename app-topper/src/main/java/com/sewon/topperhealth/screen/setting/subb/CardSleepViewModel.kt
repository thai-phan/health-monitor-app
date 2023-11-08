package com.sewon.topperhealth.screen.setting.subb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.topperhealth.R
import com.sewon.topperhealth.data.source.local.repository.SettingRepository
import com.sewon.topperhealth.data.source.local.repository.UserRepository
import com.sewon.topperhealth.data.model.Setting
import com.sewon.topperhealth.util.Async
import com.sewon.topperhealth.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalTime
import javax.inject.Inject


data class UiStateB(
  val sleepTimeStr: String = "22:00",
  val sleepTime: LocalTime = LocalTime.of(22, 0),
  val wakeupTimeStr: String = "07:00",
  val wakeupTime: LocalTime = LocalTime.of(7, 0),
  val alarmOn: Boolean = false,
  val alarmBehavior: String = "벨소리",
  val bedOn: Boolean = false,
  val isLoading: Boolean = false,
  val message: Int? = null
)

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class CardSleepViewModel @Inject constructor(
  private val userRepository: UserRepository,
  private val settingRepository: SettingRepository
) : ViewModel() {
  private var curUsername = "admin_id"

  var userId = 0
  private val _isLoading = MutableStateFlow(false)
  private val _message: MutableStateFlow<Int?> = MutableStateFlow(null)
  private var _settingAsync = settingRepository.flowLoadUserSetting(userId).map {
    handleSetting(it)
  }.catch { emit(Async.Error(R.string.setting_not_found)) }

  private val flow = MutableSharedFlow<Unit>()
  val uiState: StateFlow<UiStateB> = flow.flatMapLatest {

    combine(_settingAsync, _message, _isLoading) { settingAsync, message, isLoading ->
      when (settingAsync) {
        Async.Loading -> {
          UiStateB(isLoading = true)
        }

        is Async.Error -> {
          UiStateB(message = settingAsync.errorMessage)
        }

        is Async.Success -> {
          UiStateB(
            alarmOn = settingAsync.data!!.alarmOn,
            sleepTimeStr = settingAsync.data.sleepTime.toString(),
            sleepTime = settingAsync.data.sleepTime,
            wakeupTimeStr = settingAsync.data.wakeupTime.toString(),
            wakeupTime = settingAsync.data.wakeupTime,
            alarmBehavior = settingAsync.data.alarmBehavior,
            bedOn = settingAsync.data.bedOn,
            message = message,
            isLoading = isLoading
          )
        }
      }
    }
  }.stateIn(
    scope = viewModelScope,
    started = WhileUiSubscribed,
    initialValue = UiStateB(isLoading = true)
  )

//  private val _uiState = MutableStateFlow(UiStateB())
//  val uiState: StateFlow<UiStateA> = _uiState.asStateFlow()


//  fun loadData() {
//    val user = userRepository.getUserByUsername(curUsername)
//    if (user != null) {
//      val calendar = Calendar.getInstance()
//      calendar.time = user.birthday
//      val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
//      val birthdayString = dateFormat.format(calendar.time)
//
//      _uiState.update {
//        it.copy(
//          alarmOn = user.alarmOn,
//          sleepTimeStr = settingAsync.data.sleepTime.toString(),
//          sleepTime = settingAsync.data.sleepTime,
//          wakeupTimeStr = settingAsync.data.wakeupTime.toString(),
//          wakeupTime = settingAsync.data.wakeupTime,
//          alarmBehavior = settingAsync.data.alarmBehavior,
//          bedOn = settingAsync.data.bedOn,
//          message = message,
//          isLoading = isLoading
//        )
//      }
//    }
//  }
//
//  init {
//    loadData()
//  }

  fun changeWakeupTime(wakeupTime: LocalTime) = viewModelScope.launch {
    Timber.tag("changeSettingWakeupTime").d("changeSettingWakeupTime")
    settingRepository.updateWakeupTimeSetting(userId, wakeupTime)
    flow.tryEmit(Unit)
//    _settingAsync.retry(3) { e ->
//      Timber.tag("_settingAsync").d("_settingAsync")
//      // retry on any IOException but also introduce delay if retrying
//      (e is IOException).also { if (it) delay(1000) }
//    }
//    aa.retry()

  }

  fun changeBedTime(bedTime: LocalTime) = viewModelScope.launch {
    settingRepository.updateSleepTimeSetting(userId, bedTime)
  }

  fun changeAlarmBehavior(alarmBehavior: String) = viewModelScope.launch {
    settingRepository.updateAlarmTypeBehavior(userId, alarmBehavior)
  }

  private fun handleSetting(setting: Setting?): Async<Setting?> {
    if (setting == null) {
      return Async.Error(R.string.setting_not_found)
    }
    return Async.Success(setting)
  }
}
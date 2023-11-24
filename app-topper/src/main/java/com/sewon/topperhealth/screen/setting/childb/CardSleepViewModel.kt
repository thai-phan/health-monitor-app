package com.sewon.topperhealth.screen.setting.childb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.topperhealth.R
import com.sewon.topperhealth.data.source.local.repository.SettingRepository
import com.sewon.topperhealth.data.source.local.repository.UserRepository
import com.sewon.topperhealth.data.model.Setting
import com.sewon.topperhealth.util.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
class CardSleepViewModel @Inject constructor(
  private val userRepository: UserRepository,
  private val settingRepository: SettingRepository
) : ViewModel() {
  private var curUsername = "admin_id"

  var userId = 0

  private val _uiState = MutableStateFlow(UiStateB())
  val uiState: StateFlow<UiStateB> = _uiState.asStateFlow()

  init {
    loadData()
  }

  private fun loadData() = viewModelScope.launch {
    val setting = settingRepository.loadUserSetting(userId)
    if (setting != null) {

      _uiState.update {
        it.copy(
          alarmOn = setting.alarmOn,
          sleepTimeStr = setting.sleepTime.toString(),
          sleepTime = setting.sleepTime,
          wakeupTimeStr = setting.wakeupTime.toString(),
          wakeupTime = setting.wakeupTime,
          alarmBehavior = setting.alarmBehavior,
          bedOn = setting.bedOn,
        )
      }
    }
  }


  fun changeWakeupTime(wakeupTime: LocalTime) = viewModelScope.launch {
    Timber.tag("changeSettingWakeupTime").d("changeSettingWakeupTime")
    settingRepository.updateWakeupTimeSetting(userId, wakeupTime)
    loadData()
//    _settingAsync.retry(3) { e ->
//      Timber.tag("_settingAsync").d("_settingAsync")
//      // retry on any IOException but also introduce delay if retrying
//      (e is IOException).also { if (it) delay(1000) }
//    }
//    aa.retry()

  }

  fun changeBedTime(bedTime: LocalTime) = viewModelScope.launch {
    settingRepository.updateSleepTimeSetting(userId, bedTime)
    loadData()
  }

  fun changeAlarmBehavior(alarmBehavior: String) = viewModelScope.launch {
    settingRepository.updateAlarmTypeBehavior(userId, alarmBehavior)
    loadData()
  }

  private fun handleSetting(setting: Setting?): Async<Setting?> {
    if (setting == null) {
      return Async.Error(R.string.setting_not_found)
    }
    return Async.Success(setting)
  }
}
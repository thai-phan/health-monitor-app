package com.sewon.topperhealth.screen.setting.subd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.topperhealth.R
import com.sewon.topperhealth.data.model.Setting
import com.sewon.topperhealth.data.source.local.repository.UserRepository
import com.sewon.topperhealth.data.source.local.repository.SettingRepository
import com.sewon.topperhealth.util.Async
import com.sewon.topperhealth.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiStateD(
  val alarmTime: String = "",
  val alarmOn: Boolean = false,
  val isLoading: Boolean = false,
  val message: Int? = null
)

@HiltViewModel
class ViewModelCardSleep @Inject constructor(
  private val userRepository: UserRepository,
  private val settingRepository: SettingRepository
) : ViewModel() {

  var userId = 0
  private val _isLoading = MutableStateFlow(false)
  private val _message: MutableStateFlow<Int?> = MutableStateFlow(null)
  private var _settingAsync = settingRepository.flowLoadUserSetting(userId).map {
    handleSetting(it)
  }
    .catch { emit(Async.Error(R.string.setting_not_found)) }

  val uiState: StateFlow<UiStateD> =
    combine(_settingAsync, _message, _isLoading) { settingAsync, message, isLoading ->
      when (settingAsync) {
        Async.Loading -> {
          UiStateD(isLoading = true)
        }

        is Async.Error -> {
          UiStateD(message = settingAsync.errorMessage)
        }

        is Async.Success -> {
          UiStateD(
            alarmTime = settingAsync.data!!.wakeupTime.toString(),
            alarmOn = settingAsync.data.alarmOn,
            message = message,
            isLoading = isLoading
          )
        }
      }
    }.stateIn(
      scope = viewModelScope,
      started = WhileUiSubscribed,
      initialValue = UiStateD(isLoading = true)
    )

  fun toggleAlarmSetting(alarmOn: Boolean) = viewModelScope.launch {
//        settingRepository.updateAlarmSetting(userId, alarmOn)
  }

  private fun handleSetting(setting: Setting?): Async<Setting?> {
    if (setting == null) {
      return Async.Error(R.string.setting_not_found)
    }
    return Async.Success(setting)
  }


}
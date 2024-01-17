package com.sewon.topperhealth.ui.screen.setting.childc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.topperhealth.R
import com.sewon.topperhealth.data.model.Setting
import com.sewon.topperhealth.data.source.local.repository.UserRepository
import com.sewon.topperhealth.data.source.local.repository.SettingRepository
import com.sewon.topperhealth.util.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiStateC(
  val alarmTime: String = "",
  val alarmOn: Boolean = false,
  val isLoading: Boolean = false,
  val message: Int? = null
)

@HiltViewModel
class CardSolutionViewModel @Inject constructor(
  private val userRepository: UserRepository,
  private val settingRepository: SettingRepository
) : ViewModel() {

  var userId = 0


  fun toggleAlarmSetting(alarmOn: Boolean) = viewModelScope.launch {
//        settingRepository.updateAlarmSetting(userId, alarmOn)
  }

  fun changeInduceEnergy() {

  }

  fun changeInduceSound() {

  }

  fun changeScoreThreshold() {

  }

  private fun handleSetting(setting: Setting?): Async<Setting?> {
    if (setting == null) {
      return Async.Error(R.string.setting_not_found)
    }
    return Async.Success(setting)
  }


}
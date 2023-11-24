package com.sewon.topperhealth.screen.setting.childe

import androidx.lifecycle.ViewModel
import com.sewon.topperhealth.R
import com.sewon.topperhealth.data.model.Setting
import com.sewon.topperhealth.data.source.local.repository.SettingRepository
import com.sewon.topperhealth.data.source.local.repository.UserRepository
import com.sewon.topperhealth.util.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class UiStateE(
  val alarmTime: String = "",
  val alarmOn: Boolean = false,
  val isLoading: Boolean = false,
  val message: Int? = null
)

@HiltViewModel
class CardDeviceViewModel @Inject constructor(
  private val userRepository: UserRepository,
  private val settingRepository: SettingRepository
) : ViewModel() {

  var userId = 0

  private fun handleSetting(setting: Setting?): Async<Setting?> {
    if (setting == null) {
      return Async.Error(R.string.setting_not_found)
    }
    return Async.Success(setting)
  }
}
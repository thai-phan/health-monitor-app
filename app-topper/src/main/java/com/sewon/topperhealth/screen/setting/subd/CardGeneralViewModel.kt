package com.sewon.topperhealth.screen.setting.subd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.topperhealth.R
import com.sewon.topperhealth.data.model.Setting
import com.sewon.topperhealth.data.source.local.repository.SessionRepository
import com.sewon.topperhealth.data.source.local.repository.SettingRepository
import com.sewon.topperhealth.data.source.local.repository.TopperRepository
import com.sewon.topperhealth.screen.setting.subd.component.RecipientState
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
  val message: Int? = null,
  val relation1: String = "",
  val contact1: String = "",
  val relation2: String = "",
  val contact2: String = "",
  val relation3: String = "",
  val contact3: String = "",
  val isLoading: Boolean = false,
)

@HiltViewModel
class CardGeneralViewModel @Inject constructor(
  private val settingRepository: SettingRepository,
  private val topperRepository: TopperRepository,
  private val sessionRepository: SessionRepository,
) : ViewModel() {

  var userId = 0
  private val _isLoading = MutableStateFlow(false)
  private val _message: MutableStateFlow<Int?> = MutableStateFlow(null)
  private var _settingAsync = settingRepository.flowLoadUserSetting(userId)
  private var _settingAsyncMap = _settingAsync.map {
    handleSetting(it)
  }
    .catch { emit(Async.Error(R.string.setting_not_found)) }

  val uiState: StateFlow<UiStateD> =
    combine(_settingAsyncMap, _message, _isLoading) { settingAsync, message, isLoading ->
      when (settingAsync) {
        Async.Loading -> {
          UiStateD(isLoading = true)
        }

        is Async.Error -> {
          UiStateD(message = settingAsync.errorMessage)
        }

        is Async.Success -> {
          UiStateD(
            message = message,
            isLoading = isLoading,
            relation1 = settingAsync.data!!.relation1,
            contact1 = settingAsync.data.contact1,
            relation2 = settingAsync.data.relation2,
            contact2 = settingAsync.data.contact2,
            relation3 = settingAsync.data.relation3,
            contact3 = settingAsync.data.contact3,
          )
        }
      }
    }.stateIn(
      scope = viewModelScope,
      started = WhileUiSubscribed,
      initialValue = UiStateD(isLoading = true)
    )

  fun onChangeAccessDevice() = viewModelScope.launch {
//        settingRepository.updateAlarmSetting(userId, alarmOn)
  }

  fun onClearHistory() = viewModelScope.launch {
    topperRepository.deleteAll()
    sessionRepository.deleteAll()
  }

  fun onChangeSOSRecipient(state: RecipientState) = viewModelScope.launch {
    settingRepository.updateRecipientList(
      userId,
      state.relation1, state.contact1,
      state.relation2, state.contact2,
      state.relation3, state.contact3
    )
  }

  private fun handleSetting(setting: Setting?): Async<Setting?> {
    if (setting == null) {
      return Async.Error(R.string.setting_not_found)
    }
    return Async.Success(setting)
  }


}
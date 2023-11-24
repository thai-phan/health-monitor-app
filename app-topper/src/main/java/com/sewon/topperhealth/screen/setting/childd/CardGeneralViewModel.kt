package com.sewon.topperhealth.screen.setting.childd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.topperhealth.data.source.local.repository.SessionRepository
import com.sewon.topperhealth.data.source.local.repository.SettingRepository
import com.sewon.topperhealth.data.source.local.repository.TopperRepository
import com.sewon.topperhealth.screen.setting.childd.component.RecipientState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

  private val _uiState = MutableStateFlow(UiStateD())
  val uiState: StateFlow<UiStateD> = _uiState.asStateFlow()

  init {
    loadData()
  }

  private fun loadData() = viewModelScope.launch {
    val setting = settingRepository.loadUserSetting(userId)
    if (setting != null) {

      _uiState.update {
        it.copy(
          relation1 = setting.relation1,
          contact1 = setting.contact1,
          relation2 = setting.relation2,
          contact2 = setting.contact2,
          relation3 = setting.relation3,
          contact3 = setting.contact3,
        )
      }
    }
  }

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
    loadData()
  }
}
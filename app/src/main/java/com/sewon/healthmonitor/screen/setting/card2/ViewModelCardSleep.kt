package com.sewon.healthmonitor.screen.setting.card2

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.data.repository.UserRepository
import com.sewon.healthmonitor.data.repository.SettingRepository
import com.sewon.healthmonitor.util.WhileUiSubscribed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SleepUiState(
    val alarmOn: Boolean = false,
    val isLoading: Boolean = false,
    val message: Int? = null
)

class ViewModelCardSleep @Inject constructor(
    private val userRepository: UserRepository,
    private val settingRepository: SettingRepository
) : ViewModel() {

    var userId = 0
    private val _isLoading = MutableStateFlow(false)
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private var _settingAsync = settingRepository.loadUserSetting(userId)

    val uiState: StateFlow<SleepUiState> = _settingAsync.map { SleepUiState(alarmOn = it.alarmOn) }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = SleepUiState(isLoading = true)
    )

    fun changeGender(alarmOn: Boolean) = viewModelScope.launch {
        settingRepository.updateUserAlarm(userId, alarmOn)
    }




}
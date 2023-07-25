package com.sewon.healthmonitor.screen.setting.card1

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.data.entity.User
import com.sewon.healthmonitor.data.entity.UserSetting
import com.sewon.healthmonitor.data.repository.UserRepository
import com.sewon.healthmonitor.data.repository.UserSettingRepository
import com.sewon.healthmonitor.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


data class UiState(
    val user: User? = null,
    val isLoading: Boolean = false,
)

@HiltViewModel
class ViewModelCardProfile @Inject constructor(
    private val userSettingRepository: UserSettingRepository,
    private val userRepository: UserRepository,

    ) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private var _user: Flow<User> = userRepository.getCurrentUser("admin")

    val uiState: StateFlow<UiState> = combine(_user, _isLoading) { user, isLoading ->
        UiState(user, isLoading)
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = UiState(isLoading = true)
    )

    lateinit var userSetting: Flow<List<UserSetting>>

    //    private var _state = MutableStateFlow<State>(State.Loading)
//    val state = _state.asStateFlow()
    init {


        viewModelScope.launch {
            Log.d("Log", "onLoad viewModelScope")
//            user = userRepository.getCurrentUser("admin")
            print("asdf")

        }


//        https://stackoverflow.com/questions/73839026/jetpack-compose-displaying-data-in-compose-using-mvvm
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("Log", "onLoad CoroutineScope IO")
//            userSetting = userSettingRepository.loadUserSetting()
//            print(userSetting.first().get(0).gender)
        }
//            /* _posts.value is used now due to the datatype change */
//            _posts.value = KtorClient.httpClient.get("https://learnchn.herokuapp.com/") {
//                header("Content-Type", "application/json")
//            }
//
//            Log.d("HomeViewModel", "init: ${_posts.value[1].phrase}")
//            Log.d("HomeViewModel", "init: ${_posts.value[1].id}")
//        }
    }
//    private val _uiState = MutableStateFlow(UiState())
//    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun addUser() = viewModelScope.launch {
        var user = User("admin", "aa", "bb", "cc", "dd", "eee")
        userRepository.addUser(user)
        userSetting = userSettingRepository.loadUserSetting()
//        if (userSetting.first().size > 0) {
//            print(userSetting.first().get(0).gender)
//
//        }
//        var userSetting: Flow<List<UserSetting>> = userSettingRepository.loadUserSetting()

    }

    fun changeGender(gender: String) = viewModelScope.launch {
//        val thisUser = user.first()
//        thisUser.gender = gender
//        userRepository.updateUserSetting(thisUser)
    }

    fun changeBirthday() = viewModelScope.launch {


    }

    override fun onCleared() {
        Log.d("onCleared", "onCleared")
//        coroutineScope.cancel()
    }

}
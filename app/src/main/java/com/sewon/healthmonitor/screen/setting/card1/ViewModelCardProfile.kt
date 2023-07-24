package com.sewon.healthmonitor.screen.setting.card1

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.data.dao.UserDao
import com.sewon.healthmonitor.data.entity.User
import com.sewon.healthmonitor.data.entity.UserSetting
import com.sewon.healthmonitor.data.repository.UserRepository
import com.sewon.healthmonitor.data.repository.UserSettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewModelCardProfile @Inject constructor(
    private val userSettingRepository: UserSettingRepository,
    private val userRepository: UserRepository,

    ) : ViewModel() {

    lateinit var user: Flow<User>

    lateinit var userSetting: Flow<List<UserSetting>>
//    private var _state = MutableStateFlow<State>(State.Loading)
//    val state = _state.asStateFlow()
    init {


        viewModelScope.launch {
            Log.d("Log", "onLoad viewModelScope")

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
        user = userRepository.getCurrentUser("admin")
        val thisuser  = user.first()
        thisuser.gender = "new gender"

        userRepository.updateUserSetting(thisuser)
    }

    fun changeBirthday() = viewModelScope.launch {



    }

    override fun onCleared() {
//        coroutineScope.cancel()
    }

}
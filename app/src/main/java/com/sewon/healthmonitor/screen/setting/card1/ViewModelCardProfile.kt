package com.sewon.healthmonitor.screen.setting.card1

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.data.entity.LocalUser
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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import javax.inject.Inject


data class UiState(
    val localUser: LocalUser? = null,
    val isLoading: Boolean = false,
)

data class ProfileSettingState(
//    val user: User? = null,
    val birthdayString: String = "",
    val gender: String = "",
    val isLoading: Boolean = false,
)


@HiltViewModel
class ViewModelCardProfile @Inject constructor(
    private val userSettingRepository: UserSettingRepository,
    private val userRepository: UserRepository,

    ) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private var _user = userRepository.getCurrentUser("admin").
        .map { handleTask(it) }
        .catch { emit(Async.Error(R.string.loading_task_error)) }

    private val _uiState = MutableStateFlow(ProfileSettingState())

    val uiState: StateFlow<UiState> =
        combine(_user, _isLoading) { user, isLoading ->
            val calendar = Calendar.getInstance()
            calendar.time = user.birthday
            val dateformat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            val birthDayyy = dateformat.format(calendar.time)
            UiState(user, isLoading)
        }.stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = UiState(isLoading = true)
        )

    private fun loadUser() {
        viewModelScope.launch {
            userRepository.getCurrentUser("admin").let { user ->
                if (user != null) {
                    _uiState.update {
                        it.copy(
                            birthdayString = user.,
                            gender = user.description,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                }
            }

        }
    }


    lateinit var userSetting: Flow<List<UserSetting>>

    //    private var _state = MutableStateFlow<State>(State.Loading)
//    val state = _state.asStateFlow()
    init {

        loadUser()


//        https://stackoverflow.com/questions/73839026/jetpack-compose-displaying-data-in-compose-using-mvvm
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("asdfasdf", "CoroutineScope IO")
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
        val str = "2022-12-27"
        val df = SimpleDateFormat("yyyy-MM-dd")
        val date: Date = df.parse(str)
//        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
//        val d: Date = simpleDateFormat.parse(string_date)
//        val milliseconds = d.time
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
//        var date = Date(1346524199000)
        var localUser = LocalUser("admin_id", "admin", "aa", date, "cc", "dd", "eee")
        userRepository.addUser(localUser)
//        if (userSetting.first().size > 0) {
//            print(userSetting.first().get(0).gender)
//
//        }
//        var userSetting: Flow<List<UserSetting>> = userSettingRepository.loadUserSetting()

    }

    fun changeGender(gender: String) = viewModelScope.launch {
        userRepository.updateUserGender("admin", gender)
    }

    fun updateBirthdayState(date: Date) = viewModelScope.launch {
//        _uiState.update {
//            it.copy(description = newDescription)
//        }
        userRepository.updateUserBirthday("admin", date)
    }

    fun changeBirthday(date: Date) = viewModelScope.launch {
        userRepository.updateUserBirthday("admin", date)
    }

    override fun onCleared() {
        Log.d("asdfasdf", "onCleared")
//        coroutineScope.cancel()
    }

    private fun handleTask(task: LocalUser?): Async<Task?> {
        if (task == null) {
            return Async.Error(R.string.task_not_found)
        }
        return Async.Success(task)
    }


}
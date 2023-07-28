package com.sewon.healthmonitor.screen.setting

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.R
import com.sewon.healthmonitor.data.model.User
import com.sewon.healthmonitor.data.repository.UserRepository
import com.sewon.healthmonitor.data.repository.UserSettingRepository
import com.sewon.healthmonitor.util.Async
import com.sewon.healthmonitor.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date
import java.util.Locale
import javax.inject.Inject


data class ProfileSettingUiState(
    val calendar: Calendar = Calendar.getInstance(),
    val birthdayString: String = "",
    val gender: String = "",
    val isLoading: Boolean = false,
    val userMessage: Int? = null
)


@HiltViewModel
class ViewModelUserSetting @Inject constructor(
    private val userSettingRepository: UserSettingRepository,
    private val userRepository: UserRepository,

    ) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private var _userAsync = userRepository.getCurrentUser("admin")
        .map { handleTask(it) }
        .catch { emit(Async.Error(R.string.loading_user_error)) }

    private val _uiState = MutableStateFlow(ProfileSettingUiState())


    val uiState: StateFlow<ProfileSettingUiState> =
        combine(_userAsync, _userMessage, _isLoading) { userAsync, userMessage, isLoading ->
            when (userAsync) {
                Async.Loading -> {
                    ProfileSettingUiState(isLoading = true)
                }

                is Async.Error -> {
                    ProfileSettingUiState(userMessage = userAsync.errorMessage)
                }

                is Async.Success -> {
                    val calendar = Calendar.getInstance()
                    if (userAsync.data !== null) {
                        calendar.time = userAsync.data.birthday
                    }
                    val dateformat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                    val birthdayString = dateformat.format(calendar.time)
                    ProfileSettingUiState(
                        calendar = calendar,
                        birthdayString = birthdayString,
                        isLoading = isLoading,
                        userMessage = userMessage
                    )
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = ProfileSettingUiState(isLoading = true)
        )

    private fun loadUser() {
        viewModelScope.launch {
            if (userRepository.countUser().first() == 0) {
                val str = "1993-12-27"
                val df = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                val date: Date = df.parse(str)
                val user = User("admin_id", "admin", "남성", date, "cc", "dd", "eee")
                userRepository.addUser(user)
            }
        }
    }


    //    private var _state = MutableStateFlow<State>(State.Loading)
//    val state = _state.asStateFlow()
    init {

        loadUser()


//        https://stackoverflow.com/questions/73839026/jetpack-compose-displaying-data-in-compose-using-mvvm
        CoroutineScope(Dispatchers.IO).launch {
            Timber.d("Userrr CoroutineScope IO")
//            userSetting = userSettingRepository.loadUserSetting()
//            print(userSetting.first().get(0).gender)
        }
//            /* _posts.value is used now due to the datatype change */
//            _posts.value = KtorClient.httpClient.get("https://learnchn.herokuapp.com/") {
//                header("Content-Type", "application/json")
//            }
//        }
    }
//    private val _uiState = MutableStateFlow(UiState())
//    val uiState: StateFlow<UiState> = _uiState.asStateFlow()



    private fun handleTask(user: User?): Async<User?> {
        if (user == null) {
            return Async.Error(R.string.user_not_found)
        }
        return Async.Success(user)
    }

    override fun onCleared() {
        Timber.d("onCleared")
//        coroutineScope.cancel()
    }


}
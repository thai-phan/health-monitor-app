package com.sewon.healthmonitor.screen.setting.card1

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.R
import com.sewon.healthmonitor.data.model.User
import com.sewon.healthmonitor.data.source.local.repository.UserRepository
import com.sewon.healthmonitor.util.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject


data class ProfileUiState(
  val calendar: Calendar = Calendar.getInstance(),
  val birthday: String = "",
  val gender: String = "",
  val isLoading: Boolean = false,
  val userMessage: Int? = null
)


@HiltViewModel
class ViewModelCardProfile @Inject constructor(
  private val userRepository: UserRepository,
) : ViewModel() {
  var curUsername = "admin_id"
  private val _isLoading = MutableStateFlow(false)
  private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)

  private val _uiState = MutableStateFlow(ProfileUiState())
  val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()


  fun loadData() = viewModelScope.launch {
    val user = userRepository.getUserByUsername(curUsername)
    val calendar = Calendar.getInstance()
    calendar.time = user.birthday
    val dateformat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    val birthdayString = dateformat.format(calendar.time)

    _uiState.update {
      it.copy(
        calendar = calendar,
        gender = user.gender,
        birthday = birthdayString,
      )
    }
  }


  init {
    loadData()

//        https://stackoverflow.com/questions/73839026/jetpack-compose-displaying-data-in-compose-using-mvvm
    CoroutineScope(Dispatchers.IO).launch {
      Timber.tag("Timber").d("CoroutineScope IO")
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

  fun changeGender(gender: String) = viewModelScope.launch {
    userRepository.updateUserGender(curUsername, gender)
    loadData()
  }

  fun changeBirthday(year: Int, month: Int, day: Int) = viewModelScope.launch {
    Timber.tag("Timber").d("changeBirthday")
    val date = Calendar.getInstance().apply {
      set(year, month, day)
    }.time
    userRepository.updateUserBirthday(curUsername, date)
    loadData()
  }

  override fun onCleared() {
    Timber.tag("Timber").d("onCleared")
  }

  private fun handleUser(user: User?): Async<User?> {
    if (user == null) {
      return Async.Error(R.string.user_not_found)
    }
    return Async.Success(user)
  }
}
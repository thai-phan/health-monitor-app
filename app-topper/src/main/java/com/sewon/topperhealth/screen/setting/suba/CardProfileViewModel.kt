package com.sewon.topperhealth.screen.setting.suba

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.topperhealth.R
import com.sewon.topperhealth.data.model.User
import com.sewon.topperhealth.data.source.local.repository.UserRepository
import com.sewon.topperhealth.util.Async
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


data class UiStateA(
  val calendar: Calendar = Calendar.getInstance(),
  val birthday: String = "Please select",
  val gender: String = "1980-01-01",
  val isLoading: Boolean = false,
  val userMessage: Int? = null
)


@HiltViewModel
class ViewModelCardProfile @Inject constructor(
  private val userRepository: UserRepository,
) : ViewModel() {
  private var curUsername = "admin_id"
  private val _isLoading = MutableStateFlow(false)
  private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)

  private val _uiState = MutableStateFlow(UiStateA())
  val uiState: StateFlow<UiStateA> = _uiState.asStateFlow()


  private fun loadData() = viewModelScope.launch {
    val user = userRepository.getUserByUsername(curUsername)
    if (user != null) {
      val calendar = Calendar.getInstance()
      calendar.time = user.birthday
      val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
      val birthdayString = dateFormat.format(calendar.time)

      _uiState.update {
        it.copy(
          calendar = calendar,
          gender = user.gender,
          birthday = birthdayString,
        )
      }
    }
  }


  init {
    loadData()
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
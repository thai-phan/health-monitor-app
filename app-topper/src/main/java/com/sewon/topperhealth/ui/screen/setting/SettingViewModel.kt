package com.sewon.topperhealth.ui.screen.setting

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.topperhealth.R
import com.sewon.topperhealth.data.model.Setting
import com.sewon.topperhealth.data.model.User
import com.sewon.topperhealth.data.source.local.repository.UserRepository
import com.sewon.topperhealth.data.source.local.repository.SettingRepository
import com.sewon.topperhealth.util.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalTime
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
class SettingViewModel @Inject constructor(
  private val settingRepository: SettingRepository,
  private val userRepository: UserRepository,
) : ViewModel() {

  private fun loadUser() {
    viewModelScope.launch {
      // init first User
      if (userRepository.countUser() == 0) {
        val str = "1980-01-01"
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val date: Date = df.parse(str)
        val curDate = Calendar.getInstance().time
        val user = User("admin_id", "admin", "", date, "cc", curDate, curDate)
        userRepository.addUser(user)
      }

      // init first Setting
      if (settingRepository.countSetting() == 0) {
        val bedTime = LocalTime.of(22, 0)
        val alarmTime = LocalTime.of(7, 0)
        val setting = Setting(
          sleepTime = bedTime,
          wakeupTime = alarmTime,
        )
        settingRepository.addSetting(setting)
      }
    }
  }

  init {
    // Seed data
    loadUser()
  }

  private fun handleTask(user: User?): Async<User?> {
    if (user == null) {
      return Async.Error(R.string.user_not_found)
    }
    return Async.Success(user)
  }

  override fun onCleared() {
    Timber.tag("Timber").d("onCleared")
//        coroutineScope.cancel()
  }


}
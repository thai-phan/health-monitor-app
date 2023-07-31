package com.sewon.healthmonitor.screen.setting

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.R
import com.sewon.healthmonitor.data.source.model.Setting
import com.sewon.healthmonitor.data.source.model.User
import com.sewon.healthmonitor.data.repository.UserRepository
import com.sewon.healthmonitor.data.repository.SettingRepository
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
    private val settingRepository: SettingRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private fun loadUser() {
        viewModelScope.launch {
            if (userRepository.countUser().first() == 0) {
                val str = "1955-04-16"
                val df = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                val date: Date = df.parse(str)
                val curDate = Calendar.getInstance().time
                val user = User("admin_id", "admin", "남성", date, "cc", curDate, curDate)
                userRepository.addUser(user)
            }

            if (settingRepository.countSetting().first() == 0) {
                val curDate = Calendar.getInstance().time
                val setting = Setting(
                    userId = 0,
                    alarmTime = "07:00",
                    bedTime = "22:00",
                    alarmOn = false,
                    energyOn = false,
                    soundOn = false,
                    cacheOn = false,
                    initOn = false,
                    sosOn = false,
                    productSn = "aa",
                    threshold = "threshold",
                    createdAt = curDate,
                    updatedAt = curDate,
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
        Timber.d("onCleared")
//        coroutineScope.cancel()
    }


}
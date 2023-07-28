package com.sewon.healthmonitor.screen.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.data.source.local.entity.Radar
import com.sewon.healthmonitor.data.repository.repointerface.IRadarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class UiState(
    val status1: String = "",
    val status2: String = "",
    val status3: String = "",
    val description: String = "",
    val isTaskCompleted: Boolean = false,
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
    val isTaskSaved: Boolean = false
)


@HiltViewModel
class ViewModelSleepActivity @Inject constructor(
    private val IRadarRepository: IRadarRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    fun createNewTask() = viewModelScope.launch {
        val radar = Radar(0.1f, 0.1f, 0.1f, "X", "X", "X", "X")

        IRadarRepository.createTopper(radar)
        _uiState.update {
            it.copy(status1 = "Ahahahah")
        }
    }

    fun getToppers() = viewModelScope.launch {

        var aaa = IRadarRepository.getTopper()

        _uiState.update {
            it.copy(status2 = aaa.first().get(0).moving)
        }
    }

    fun getCount() = viewModelScope.launch {

        var bbb = IRadarRepository.getCountTopper()

        _uiState.update {
            it.copy(status3 = bbb.first().toString())
        }
    }


}
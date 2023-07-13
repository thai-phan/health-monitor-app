package com.sewon.healthmonitor.screen.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.data.entity.Topper
import com.sewon.healthmonitor.data.repository.TopperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
class SleepActivityViewModel @Inject constructor(
    private val topperRepository: TopperRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    fun createNewTask() = viewModelScope.launch {
        val topper = Topper(0.1f, 0.1f, 0.1f, "X", "X", "X", "X")

        topperRepository.createTopper(topper)
        _uiState.update {
            it.copy(status1 = "Ahahahah")
        }
    }
}
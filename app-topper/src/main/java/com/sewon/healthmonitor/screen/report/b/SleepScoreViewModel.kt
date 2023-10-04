package com.sewon.healthmonitor.screen.report.b

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.data.repository.repointerface.ISessionRepository
import com.sewon.healthmonitor.data.source.local.entity.LocalSession
import com.sewon.healthmonitor.service.algorithm.sleep.database.ReportData
import com.sewon.healthmonitor.service.algorithm.sleep.database.ReportAlgorithm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class UiStateB(
  val sleepRating: Float = 0.0f,
  val sleepStage: List<Float> = listOf(),
  val sleepRPI: List<Float> = listOf(),
)

@HiltViewModel
class SleepScoreViewModel @Inject constructor(
  private val sessionRepository: ISessionRepository,
) : ViewModel() {

  private val _uiStateB = MutableStateFlow(UiStateB())
  val uiStateB: StateFlow<UiStateB> = _uiStateB.asStateFlow()

  private fun initFirstSession() {
    viewModelScope.launch {

    }
  }

  init {
    initFirstSession()
  }

  fun loadData() {
    getSleepScore()
    getSleepStage()
    getSleepRPI()
  }

  fun getSleepScore() = viewModelScope.launch {
    val session: LocalSession = sessionRepository.getSessionById(1)
    _uiStateB.update {
      it.copy(sleepRating = session.rating.toFloat())
    }
  }

  fun getSleepStage() = viewModelScope.launch {
    //    TODO: (Done) query all data -> Sleep Stage
    val session: LocalSession = sessionRepository.getSessionById(1)

    ReportAlgorithm.refHRV = session.refHRV
    ReportAlgorithm.refHR = session.refHR
    ReportAlgorithm.refBR = session.refBR

    val sleepStage = ReportAlgorithm.processByTime()
    _uiStateB.update {
      it.copy(sleepStage = sleepStage)
    }
  }

  fun getSleepRPI() = viewModelScope.launch {
//    TODO: (Done) query all data -> Sleep RPI
    val sleepRPI: List<Float> = ReportData.getBSleepRPI()

    _uiStateB.update {
      it.copy(sleepRPI = sleepRPI)
    }
  }
}

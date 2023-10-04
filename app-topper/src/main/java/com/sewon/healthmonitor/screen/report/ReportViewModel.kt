package com.sewon.healthmonitor.screen.report


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.data.model.Session
import com.sewon.healthmonitor.data.repository.repointerface.ISessionRepository
import com.sewon.healthmonitor.data.repository.repointerface.IUserRepository
import com.sewon.healthmonitor.data.source.local.entity.LocalSession
import com.sewon.healthmonitor.service.algorithm.sleep.database.ReportAlgorithm
import com.sewon.healthmonitor.service.algorithm.sleep.database.ReportData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject


data class UiState(
  val sleepTime: Float = 0.0f,
  val sleepEfficiency: Float = 0.0f,
  val sleepLatency: Float = 0.0f,
  val wakeupOnSleep: Float = 0.0f,

  val sleepRating: Float = 0.0f,
  val sleepStage: List<Float> = listOf(),
  val sleepRPI: List<Float> = listOf(),

  val meanHR: Float = 0.0f,
  val meanBR: Float = 0.0f,
  val SDRP: Float = 0.0f,
  val RMSSD: Float = 0.0f,
  val RPITriangular: Float = 0.0f,
  val lowFreq: Float = 0.0f,
  val highFreq: Float = 0.0f,
  val lfHfRatio: Float = 0.0f,

  val nervousScore: List<Float> = listOf(),
  val stressScore: Float = 0.0f,
)

@HiltViewModel
class ReportViewModel @Inject constructor(
  private val userRepository: IUserRepository,
  private val sessionRepository: ISessionRepository,
) : ViewModel() {

  private val _uiState = MutableStateFlow(UiState())
  val uiState: StateFlow<UiState> = _uiState.asStateFlow()


  private fun initFirstSession() {
    viewModelScope.launch {
      if (sessionRepository.countSession().first() == 0) {
        val curTime = LocalTime.now()
        val session = Session(
          0.0, 0.0, 0.0,
          0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
          50, 10,
          curTime, curTime, curTime.plusHours(3)
        )
        sessionRepository.addSession(session)
      }
    }
  }

  init {
    initFirstSession()
  }

  fun loadData() = viewModelScope.launch {
    val session: LocalSession = sessionRepository.getSessionById(1)
    ReportAlgorithm.refHRV = session.refHRV
    ReportAlgorithm.refHR = session.refHR
    ReportAlgorithm.refBR = session.refBR

    getTotalSleepTime(session)
    getSleepEfficiency(session)
    getSleepLatency(session)
    getWakeOnSleep(session)

    getSleepScore(session)
    getSleepStage(session)
    getSleepRPI(session)

    getMeanHR(session)
    getMeanBR(session)
    getECG(session)
    getRPITriangular(session)

    getNervousScore(session)
    getSummarySleep(session)
  }

  fun getTotalSleepTime(session : LocalSession) = viewModelScope.launch {
    val startTime: LocalTime = session.startTime
    val wakeUpTime: LocalTime = session.endTime
    val sleepTime = startTime.until(wakeUpTime, ChronoUnit.HOURS).toFloat()
    _uiState.update {
      it.copy(sleepTime = sleepTime)
    }
  }

  fun getSleepEfficiency(session : LocalSession) = viewModelScope.launch {
    val sleepEfficiency = session.rating.toFloat()
    _uiState.update {
      it.copy(sleepEfficiency = sleepEfficiency)
    }
  }

  fun getSleepLatency(session : LocalSession) = viewModelScope.launch {
    val startTime: LocalTime = session.startTime
    val sleepTime: LocalTime = session.sleepTime
    val sleepLatency = startTime.until(sleepTime, ChronoUnit.MINUTES).toFloat()
    _uiState.update {
      it.copy(sleepLatency = sleepLatency)
    }
  }

  fun getWakeOnSleep(session : LocalSession) = viewModelScope.launch {
    val wakeupOnSleep = session.wakeUpCount.toFloat()
    _uiState.update {
      it.copy(wakeupOnSleep = wakeupOnSleep)
    }
  }

  fun getSleepScore(session : LocalSession) = viewModelScope.launch {
    _uiState.update {
      it.copy(sleepRating = session.rating.toFloat())
    }
  }

  fun getSleepStage(session : LocalSession) = viewModelScope.launch {
    val sleepStage = ReportAlgorithm.processByTime()
    _uiState.update {
      it.copy(sleepStage = sleepStage)
    }
  }

  fun getSleepRPI(session : LocalSession) = viewModelScope.launch {
    val sleepRPI: List<Float> = ReportData.getBSleepRPI()

    _uiState.update {
      it.copy(sleepRPI = sleepRPI)
    }
  }

  fun getMeanHR(session : LocalSession) = viewModelScope.launch {
    val meanHR = ReportData.getCMeanHR()
    _uiState.update {
      it.copy(meanHR = meanHR)
    }
  }

  fun getMeanBR(session : LocalSession) = viewModelScope.launch {
    val meanBR = ReportData.getCMeanBR()
    _uiState.update {
      it.copy(meanBR = meanBR)
    }
  }

  fun getECG(session : LocalSession) = viewModelScope.launch {
    val ECGTopper = ReportData.getECGAlgorithmResult()
    if (ECGTopper != null) {
      _uiState.update {
        it.copy(
          SDRP = ECGTopper.SDRP.toFloat(),
          RMSSD = ECGTopper.RMSSD.toFloat(),
          lowFreq = ECGTopper.LF.toFloat(),
          highFreq = ECGTopper.HF.toFloat(),
          lfHfRatio = ECGTopper.LF_HF_Ratio.toFloat()
        )
      }
    }
  }

  fun getRPITriangular(session : LocalSession) = viewModelScope.launch {
    val RPITriangular = ReportData.getRPITriangular()
    _uiState.update {
      it.copy(RPITriangular = RPITriangular)
    }
  }

  fun getNervousScore(session : LocalSession) = viewModelScope.launch {
    val session: LocalSession = sessionRepository.getSessionById(1)

    val nervousScore = listOf<Float>()
    _uiState.update {
      it.copy(nervousScore = nervousScore)
    }
  }

  fun getSummarySleep(session : LocalSession) = viewModelScope.launch {
    //    TODO: query all data -> Sleep Stage
    val session: LocalSession = sessionRepository.getSessionById(1)
//    ReportAlgorithm.processReport(session.refHRV, session.refHR, session.refBR)
    val stressScore = session.rating.toFloat()
    _uiState.update {
      it.copy(stressScore = stressScore)
    }
  }
}



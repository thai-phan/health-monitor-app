package com.sewon.healthmonitor.screen.report


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.data.repository.repointerface.ISessionRepository
import com.sewon.healthmonitor.data.repository.repointerface.IUserRepository
import com.sewon.healthmonitor.data.source.local.entity.LocalSleepSession
import com.sewon.healthmonitor.service.algorithm.sleep.database.ReportAlgorithm
import com.sewon.healthmonitor.service.algorithm.sleep.database.ReportDataProcessing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date
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
//      if (sessionRepository.countSession().first() == 0) {
//        val curTime = LocalTime.now()
//        val session = Session(
//          0.0, 0.0, 0.0,
//          0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
//          50, 10,
//          curTime, curTime, curTime.plusHours(3)
//        )
//        sessionRepository.addSession(session)
//      }
    }
  }

  init {
    initFirstSession()
  }

  var session: LocalSleepSession? = null

  fun showData(sessionId: Int) = viewModelScope.launch {
    session = sessionRepository.getSessionById(sessionId)

    if (session != null) {
      ReportAlgorithm.refHRV = session!!.refHRV
      ReportAlgorithm.refHR = session!!.refHR
      ReportAlgorithm.refBR = session!!.refBR

      getTotalSleepTime()
      getSleepEfficiency()
      getSleepLatency()
      getWakeOnSleep()

      getSleepScore()
      getSleepStage()
      getSleepRPI()

      getMeanHR()
      getMeanBR()
      getECG()
      getRPITriangular()

      getNervousScore()
      getSummarySleep()
    }

  }

  fun getTotalSleepTime() {
    val startTime: Date = session!!.actualStartTime
    val wakeUpTime: Date = session!!.endTime
    val sleepTime = Date(wakeUpTime.getTime() - startTime.getTime()).time.toFloat()
    Timber.tag("Timber").d(sleepTime.toString())
//    val sleepTime = startTime.until(wakeUpTime, ChronoUnit.HOURS).toFloat()
    _uiState.update {
      it.copy(sleepTime = sleepTime)
    }
  }

  fun getSleepEfficiency() {
    val sleepEfficiency = session!!.rating.toFloat()
    _uiState.update {
      it.copy(sleepEfficiency = sleepEfficiency)
    }
  }

  fun getSleepLatency() {
    val startTime: Date = session!!.actualStartTime
    val sleepTime: Date = session!!.sleepTime
    val sleepLatency = Date(sleepTime.getTime() - startTime.getTime()).time.toFloat()
//    val sleepLatency = startTime.until(sleepTime, ChronoUnit.MINUTES).toFloat()
    _uiState.update {
      it.copy(sleepLatency = sleepLatency)
    }
  }

  fun getWakeOnSleep() {
    val wakeupOnSleep = session!!.wakeUpCount.toFloat()
    _uiState.update {
      it.copy(wakeupOnSleep = wakeupOnSleep)
    }
  }

  fun getSleepScore() {
    _uiState.update {
      it.copy(sleepRating = session!!.rating.toFloat())
    }
  }

  fun getSleepStage() {
    val sleepStage = ReportAlgorithm.processByTime()
    _uiState.update {
      it.copy(sleepStage = sleepStage)
    }
  }

  fun getSleepRPI() {
    val sleepRPI: List<Float> = ReportDataProcessing.getBSleepRPI()
    _uiState.update {
      it.copy(sleepRPI = sleepRPI)
    }
  }

  fun getMeanHR() {
    val meanHR = ReportDataProcessing.getCMeanHR()
    _uiState.update {
      it.copy(meanHR = meanHR)
    }
  }

  fun getMeanBR() {
    val meanBR = ReportDataProcessing.getCMeanBR()
    _uiState.update {
      it.copy(meanBR = meanBR)
    }
  }

  fun getECG() {
    val topper = ReportDataProcessing.getECGAlgorithmResult()
    if (topper != null) {
      _uiState.update {
        it.copy(
          SDRP = topper.SDRP.toFloat(),
          RMSSD = topper.RMSSD.toFloat(),
          lowFreq = topper.LF.toFloat(),
          highFreq = topper.HF.toFloat(),
          lfHfRatio = topper.LF_HF_Ratio.toFloat()
        )
      }
    }
  }

  fun getRPITriangular() {
    val RPITriangular = ReportDataProcessing.getRPITriangular()
    _uiState.update {
      it.copy(RPITriangular = RPITriangular)
    }
  }

  fun getNervousScore() {
    //    TODO: query all data -> Sleep Stage
    val nervousScore = listOf(session!!.lowFreq.toFloat(), session!!.highFreq.toFloat())
    _uiState.update {
      it.copy(nervousScore = nervousScore)
    }
  }

  fun getSummarySleep() {
    val stressScore = 0.0f
    _uiState.update {
      it.copy(stressScore = stressScore)
    }
  }
}



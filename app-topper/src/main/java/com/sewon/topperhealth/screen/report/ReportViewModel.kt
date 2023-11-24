package com.sewon.topperhealth.screen.report


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.topperhealth.data.model.SleepSession
import com.sewon.topperhealth.data.irepository.ISessionRepository
import com.sewon.topperhealth.data.irepository.ITopperRepository
import com.sewon.topperhealth.service.algorithm.sleep.report.ReportHandler
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
  val sessionList: List<SleepSession> = listOf(),

//  Chart
  val sleepTime: Float = 0.0f,
  val sleepEfficiency: Float = 0.0f,
  val sleepLatency: Float = 0.0f,
  val wakeupOnSleep: Float = 0.0f,

  val sleepRating: Float = 0.0f,
  val sleepStage: List<Float> = listOf(),
  val sleepRPI: List<Float> = listOf(),
  val meanHR: Float = 0.0f,
  val meanBR: Float = 0.0f,
  val sDRP: Float = 0.0f,
  val rMSSD: Float = 0.0f,
  val rPITriangular: Float = 0.0f,
  val lowFreq: Float = 0.0f,
  val highFreq: Float = 0.0f,
  val lfHfRatio: Float = 0.0f,
  val nervousScore: List<Float> = listOf(),
  val stressScore: Float = 0.0f,

  val refHRV: Double = 0.0,
  val refHR: Double = 0.0,
  val refBR: Double = 0.0,
  val totalRecord: Int = 0
)

@HiltViewModel
class ReportViewModel @Inject constructor(
  private val topperRepository: ITopperRepository,
  private val sessionRepository: ISessionRepository,
) : ViewModel() {

  private val _uiState = MutableStateFlow(UiState())
  val uiState: StateFlow<UiState> = _uiState.asStateFlow()


  private fun initFirstSession() {
    viewModelScope.launch {
      val sessionList = sessionRepository.getSleepSessionList()
      _uiState.update {
        it.copy(sessionList = sessionList)
      }
    }
  }

  init {
    initFirstSession()
  }


  fun showSessionReport(sessionId: Int) = viewModelScope.launch {
    val sessionData = topperRepository.getAllDataFromSession(sessionId)
    val session = sessionRepository.getSessionById(sessionId)

    // Debug state
    _uiState.update {
      it.copy(
        totalRecord = sessionData.size,
        refHRV = session.refHRV,
        refHR = session.refHR,
        refBR = session.refBR
      )
    }
    if (sessionData.isNotEmpty()) {
      val reportHandler = ReportHandler(sessionData, session.refHRV, session.refHR, session.refBR)


      val sleepEfficiency = session.rating.toFloat()
      val startTime: Date = session.actualStartTime
      val wakeUpTime: Date = session.actualEndTime
      val totalSleepTime = Date(wakeUpTime.time - startTime.time).time.toFloat()
      val sleepTime: Date = session.sleepTime
      val sleepLatency = Date(sleepTime.time - startTime.time).time.toFloat()
      val wakeupOnSleep = session.wakeUpCount.toFloat()
      val sleepStage = reportHandler.getSleepStage()

      val sleepRPI: List<Float> = reportHandler.getBSleepRPI()
      val meanHR = reportHandler.getCMeanHR()
      val meanBR = reportHandler.getCMeanBR()
      val ecgTopper = reportHandler.getECGAlgorithmResult()
      val nervousScore = listOf(ecgTopper.LF.toFloat(), ecgTopper.HF.toFloat())
      val rPITriangular = reportHandler.getRPITriangular()

      _uiState.update {
        it.copy(
          sleepTime = totalSleepTime / 12,
          sleepEfficiency = sleepEfficiency / 100,
          sleepLatency = sleepLatency / 60,
          wakeupOnSleep = wakeupOnSleep / 60,
          sleepRating = session.rating.toFloat() * 20,
          sleepStage = sleepStage,

          //  Hide
          sleepRPI = sleepRPI,
          meanHR = meanHR,
          meanBR = meanBR,
          sDRP = ecgTopper.SDRP.toFloat(),
          rMSSD = ecgTopper.RMSSD.toFloat(),
          lowFreq = ecgTopper.LF.toFloat(),
          highFreq = ecgTopper.HF.toFloat(),
          lfHfRatio = ecgTopper.LF_HF_Ratio.toFloat(),
          nervousScore = nervousScore,
          stressScore = ecgTopper.StressScore.toFloat(),
          rPITriangular = rPITriangular
        )
      }
    } else {
      // TODO:
//      ReportAlgorithm.refHRV = 0.0
//      ReportAlgorithm.refHR = 0.0
//      ReportAlgorithm.refBR = 0.0
      Timber.tag("ReportViewModel").d("showSessionReport error")
    }
  }
}



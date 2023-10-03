package com.sewon.healthmonitor.screen.report.c

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.data.repository.repointerface.ISensorRepository
import com.sewon.healthmonitor.data.repository.repointerface.ISessionRepository
import com.sewon.healthmonitor.service.algorithm.sleep.database.ReportData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class UiStateC(
  val meanHR: Float = 0.0f,
  val meanBR: Float = 0.0f,
  val SDRP: Float = 0.0f,
  val RMSSD: Float = 0.0f,
  val RPITriangular: Float = 0.0f,
  val lowFreq: Float = 0.0f,
  val highFreq: Float = 0.0f,
  val lfHfRatio: Float = 0.0f,

  )

@HiltViewModel
class SleepDetailViewModel @Inject constructor(
  private val sessionRepository: ISessionRepository,
  private val sensorRepository: ISensorRepository,

  ) : ViewModel() {

  private val _uiStateC = MutableStateFlow(UiStateC())
  val uiStateC: StateFlow<UiStateC> = _uiStateC.asStateFlow()

  private fun initFirstSession() {
    viewModelScope.launch {

    }
  }

  init {
    initFirstSession()
  }

  fun loadData() {
    getMeanHR()
    getMeanBR()
    getECG()
    getRPITriangular()
  }


  fun getMeanHR() = viewModelScope.launch {
    val meanHR = ReportData.getCMeanHR()
    _uiStateC.update {
      it.copy(meanHR = meanHR)
    }
  }

  fun getMeanBR() = viewModelScope.launch {
    val meanBR = ReportData.getCMeanBR()
    _uiStateC.update {
      it.copy(meanBR = meanBR)
    }
  }

  fun getECG() = viewModelScope.launch {
    val ECGTopper = ReportData.getECGAlgorithmResult()
    if (ECGTopper != null) {
      _uiStateC.update {
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

  fun getRPITriangular() = viewModelScope.launch {
    val RPITriangular = ReportData.getRPITriangular()
    _uiStateC.update {
      it.copy(RPITriangular = RPITriangular)
    }
  }
}

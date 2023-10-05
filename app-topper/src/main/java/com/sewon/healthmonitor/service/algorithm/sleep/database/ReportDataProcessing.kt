package com.sewon.healthmonitor.service.algorithm.sleep.database

import com.sewon.healthmonitor.data.source.local.entity.LocalTopper
import com.sewon.healthmonitor.service.algorithm.ecg.ECGAnalysisProc
import com.sewon.healthmonitor.service.algorithm.ecg.ECGTopper


class ReportDataProcessing {

  companion object {

    lateinit var allData: List<LocalTopper>

    fun importData(data: List<LocalTopper>) {
      allData = data
    }

    fun getBSleepRPI(): List<Float> {
      return allData.map { it.hrv.toFloat() }
    }

    fun getCMeanHR(): Float {
      return allData.map { it.hr }.average().toFloat()
    }

    fun getCMeanBR(): Float {
      return allData.map { it.br }.average().toFloat()
    }


    fun getECGAlgorithmResult(): ECGTopper? {
      val HRVArray: DoubleArray = allData.map { it.hrv }.toDoubleArray()
      return ECGAnalysisProc.ECG_PPG_AnalysisData(HRVArray)
    }

    fun getRPITriangular(): Float {
      val HRVList = allData.map { it.hrv }
      return mostCommon(HRVList).toFloat()
    }

    fun <T> mostCommon(list: List<T>): T {
      val map: MutableMap<T, Int> = HashMap()
      for (t in list) {
        val value = map[t]
        map[t] = if (value == null) 1 else value + 1
      }
      var max: Map.Entry<T, Int>? = null
      for (e in map.entries) {
        if ((max == null) || (e.value > max.value)) max = e
      }
      return max!!.key
    }
  }


}
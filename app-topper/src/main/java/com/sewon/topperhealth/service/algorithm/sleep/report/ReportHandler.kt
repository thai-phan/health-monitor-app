package com.sewon.topperhealth.service.algorithm.sleep.report

import android.content.Context
import android.widget.Toast
import com.sewon.topperhealth.api.sewon.ServiceSewon
import com.sewon.topperhealth.data.model.SleepSession
import com.sewon.topperhealth.data.source.local.entity.LocalTopper
import com.sewon.topperhealth.service.algorithm.ecg.ECGAnalysisProc
import com.sewon.topperhealth.service.algorithm.ecg.ECGTopper
import com.sewon.topperhealth.service.algorithm.sleep.AlgorithmConstants
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.io.IOException


class ReportHandler(
  private var sessionData: List<LocalTopper>,
  private var sleepSession: SleepSession,
) {
  val tag = "TimberReportHandler"

  fun getBSleepRPI(): List<Float> {
    return sessionData.map { it.hrv.toFloat() }
  }

  fun getCMeanHR(): Float {
    return sessionData.map { it.hr }.average().toFloat()
  }

  fun getCMeanBR(): Float {
    return sessionData.map { it.br }.average().toFloat()
  }

  fun getECGAlgorithmResult(): ECGTopper {
    val HRVArray: DoubleArray = sessionData.map { it.hrv }.toDoubleArray()
    return ECGAnalysisProc.ECG_PPG_AnalysisData(HRVArray)
  }

  fun getRPITriangular(): Float {
    val HRVList = sessionData.map { it.hrv }
    return mostCommon(HRVList).toFloat()
  }

  private fun <T> mostCommon(list: List<T>): T {
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

  suspend fun getSleepStageCloud(context: Context): MutableList<Float> {
//    State,HR,BR,HRV,HRwfm,BRwfm,SleepCategory
    val stageList = mutableListOf<Float>()

    try {
      val root = File(context.dataDir, "csv")
      if (!root.exists()) {
        root.mkdirs()
      }
      val csvFileName = "sleep_data_${sleepSession.sessionId}.csv"
      val csvFile = File(root, csvFileName)
      if (!csvFile.exists()) {
        val writer = FileWriter(csvFile)
        writer.append("State,HR,BR,HRV,HRwfm,BRwfm\n")
        for (data in sessionData) {
          writer.append("${data.stable},${data.hr},${data.br},${data.hrv},${data.hrWfm},${data.brWfm}\n")
        }
        writer.flush()
        writer.close()
        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
      } else {
        Toast.makeText(context, "Not create", Toast.LENGTH_SHORT).show()
      }

      val multipartBody = MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart(
        "file", "", csvFile.asRequestBody("application/octet-stream".toMediaType())
      ).build()
      val response = ServiceSewon.create().getAlgorithm(multipartBody)
      val responseList = response.string().split("\n")
      val responseListNoHeader = responseList.subList(1, responseList.size)
      Timber.tag("report1").d(responseListNoHeader.size.toString())
      for (row in responseListNoHeader) {
        val rowElement = row.split(",")
        if (rowElement.size == 7) {
          stageList.add(rowElement.get(6).toFloat())
        } else {
          Timber.tag("report2").d("row empty")
        }
      }
    } catch (e: IOException) {
      e.printStackTrace()
    }
    return if (stageList.size <= 100) {
      stageList
    } else {
      stageList.subList(0, 100)

    }
  }

  private fun calculateSleepStageByTime(stageList: List<Int>): MutableList<Float> {
    var sumStage = 0
    val stageTime = mutableListOf<Float>()
    var countTime = 0

    for (stage in stageList) {
      if (countTime == AlgorithmConstants.SLEEP_STAGE_NUMBER) {
        val mean = sumStage / countTime
        Timber.tag("report5").d(sumStage.toString())
        Timber.tag("report3").d(mean.toString())
        stageTime.add(mean.toFloat())
        sumStage = 0
        countTime = 0
      }
      sumStage += stage
      countTime += 1
    }
    Timber.tag("report4").d(stageTime.toString())
    return stageTime
  }


  fun sewonSource(context: Context) {
    val thread = Thread {
      try {
        val root = File(context.dataDir, "csv")
        if (!root.exists()) {
          root.mkdirs()
        }
        val filecsv = File(root, "data_small.csv")
        val client = OkHttpClient()
        val body = MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart(
          "file", "", filecsv.asRequestBody("application/octet-stream".toMediaType())
        ).build()
        val request =
          Request.Builder().url("http://175.196.118.115:8080/predict").post(body).build()
        val response: Response = client.newCall(request).execute()
        val jsonData: String = response.body!!.string()
        // Transform reponse to JSon Object
        println(jsonData)
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }

    thread.start()
  }

  fun getSleepStage(): List<Float> {
    var sumHRV = 0.0
    var sumHR = 0.0
    var sumBR = 0.0
    val stageList = mutableListOf<Float>()
    var countTime = 0

    for (data in sessionData) {
      if (countTime == AlgorithmConstants.SLEEP_STAGE_NUMBER) {
        val meanHRV = sumHRV / countTime
        val meanHR = sumHR / countTime
        val meanBR = sumBR / countTime
        val stage = calculatorSleepStage(meanHRV, meanHR, meanBR)
        stageList.add(stage.toFloat())

        sumHRV = 0.0
        sumHR = 0.0
        sumBR = 0.0
        countTime = 0
      }
      sumHRV += data.hrv
      sumHR += data.hr
      sumBR += data.br
      countTime += 1
    }
    return stageList
  }

  private fun calculatorSleepStage(meanHrv: Double, meanHr: Double, meanBr: Double): Int {
    if (meanHrv < sleepSession.refHRV * AlgorithmConstants.SLEEP_HRV_THRESHOLD) {
      return AlgorithmConstants.SLEEP_STAGE_N3_REM
    } else {
      return if (meanHr >= sleepSession.refHR * AlgorithmConstants.SLEEP_HR_THRESHOLD) {
        if (meanBr >= sleepSession.refBR * AlgorithmConstants.SLEEP_HRUP_BR_THRESHOLD) {
          AlgorithmConstants.SLEEP_STAGE_N1_N2
        } else {
          AlgorithmConstants.SLEEP_STAGE_N3_REM
        }
      } else {
        if (meanBr >= sleepSession.refBR * AlgorithmConstants.SLEEP_HRDOWN_BR_THRESHOLD) {
          AlgorithmConstants.SLEEP_STAGE_WAKE
        } else {
          AlgorithmConstants.SLEEP_STAGE_N1_N2
        }
      }
    }
  }

}
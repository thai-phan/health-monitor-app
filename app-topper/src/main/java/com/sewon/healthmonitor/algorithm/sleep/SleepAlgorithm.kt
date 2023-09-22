package com.sewon.healthmonitor.algorithm.sleep

class SleepAlgorithm {

  companion object {
    var firstReferenceCount = 3 * 60 * 20
    var sumHRV = 0.0
    var sumHR = 0.0
    var sumBR = 0.0


    var firstMeanHRV = 0.0
    var firstMeanHR = 0.0
    var firstMeanBR = 0.0

    var countReferenceData = 0



    fun calculateFirstMean() {
      firstMeanHRV = 1.0
      firstMeanHR = 1.0
      firstMeanBR = 1.0
    }


    fun inputData(dataList: List<String>) {
      dataList


      countReferenceData += 1

      if (countReferenceData < firstReferenceCount) {

      }

      if (countReferenceData  == firstReferenceCount) {

      }
    }



  }


  val HRVTheshold = 1.2
  val HRThreshold = 0.9
  val BRThreshold = 1




  fun getCurrentLux() {

  }


  fun checkSensorData() {
    var curHRV = 0
    var curHR = 0
    var curBR = 0
    if (curHRV > firstMeanHRV * HRVTheshold) {
      if (curHR < firstMeanHR * HRThreshold) {
        if (curBR < firstMeanBR * BRThreshold) {
          sleepStart()
        }
      }
    }
    // Check HRV over firstMean
  }

  var countInsomniaValue = 0
  var countDeepSleep = 0

  fun sleepStart(stringList: List<String>) {
    var stable = stringList[0].toInt()
    var hr = stringList[1].toInt()
    var br = stringList[2].toInt()
    var hrv = stringList[3].toDouble()
    var hrWfm = stringList[4].toDouble()
    var brWfm = stringList[5].toDouble()

    if (stable == 0 || stable == 1) {
      if (countInsomniaValue == 1200) {
        callInsomnia()
      }
      countInsomniaValue += 1
    } else {
      countInsomniaValue = 0
    }

    if (stable == 2) {
      if (countDeepSleep == 1200) {
        callDeepSleep()
      }
      countDeepSleep += 1
    } else {
      countDeepSleep = 0
    }
  }

  fun callInsomnia(stringList: List<String>) {
    var stable = stringList[0].toInt()
    var hr = stringList[1].toInt()
    var br = stringList[2].toInt()
    var hrv = stringList[3].toDouble()
    var hrWfm = stringList[4].toDouble()
    var brWfm = stringList[5].toDouble()

    if (hr != 0 && br != 0) {
      if (hr - firstMeanHR > 5 || firstMeanHR - hr > 5) {
        saveDatabase()
      }
    } else {
      saveDatabase(noiseData)

    }


  }

  fun callDeepSleep() {
    var curTime = 0
    var sleepTime = 0
    var isWakeup = true
    if (curTime < sleepTime) {
      saveDatabase()
    } else {
      callAlarm()
      if (hr != 0 && br != 0) {
        if (hr - firstMeanHR > 5 || firstMeanHR - hr > 5) {
          saveDatabase()
        }
      } else {
        saveDatabase(noiseData)
      }
    }
  }

  fun callAlarm() {

  }

  fun saveDatabase() {

  }

  fun





  // Sleep ble data

  // First 3 min data

  // Mean HRV, HR, BR

  // Lux check < 10

  //


}
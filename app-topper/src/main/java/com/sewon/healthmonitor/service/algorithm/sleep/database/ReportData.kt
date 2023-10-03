package com.sewon.healthmonitor.service.algorithm.sleep.database

import com.sewon.healthmonitor.data.source.local.entity.LocalSensor

class ReportData {

  companion object {

    lateinit var allData: List<LocalSensor>

    fun importData(data: List<LocalSensor>) {
      allData = data
    }


  }


}
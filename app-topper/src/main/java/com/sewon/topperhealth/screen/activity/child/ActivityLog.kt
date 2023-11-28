package com.sewon.topperhealth.screen.activity.child

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.sewon.topperhealth.service.algorithm.sleep.realtime.RealtimeHandler
import com.sewon.topperhealth.service.bluetooth.LowEnergyClient

@Composable
fun ActivityLog() {

  val log by LowEnergyClient.log.observeAsState()
  val count by RealtimeHandler.countReferenceData.observeAsState()
  val refHRV by RealtimeHandler.refHRV.observeAsState()
  val refHR by RealtimeHandler.refHR.observeAsState()
  val refBR by RealtimeHandler.refBR.observeAsState()


  Column {
    Text("Log: ${log}")
    Text("Total: ${count}")
    Text("HR: ${refHR}")
    Text("BR: ${refBR}")
    Text("HRV: ${refHRV}")
  }
}
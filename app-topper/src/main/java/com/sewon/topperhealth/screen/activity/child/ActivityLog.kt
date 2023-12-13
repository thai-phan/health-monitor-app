package com.sewon.topperhealth.screen.activity.child

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.sewon.topperhealth.screen.activity.ActivityViewModel
import com.sewon.topperhealth.service.algorithm.sleep.realtime.RealtimeHandler
import com.sewon.topperhealth.service.bluetooth.LowEnergyClient

@Composable
fun ActivityLog(viewModel: ActivityViewModel) {

  val log by LowEnergyClient.log.observeAsState()
  val count by RealtimeHandler.countReferenceData.observeAsState()
//  val referenceCount by RealtimeHandler.referenceCount.observeAsState()

  val refHRV by RealtimeHandler.refHRV.observeAsState()
  val refHR by RealtimeHandler.refHR.observeAsState()
  val refBR by RealtimeHandler.refBR.observeAsState()

  fun testOpenAI() {
    viewModel.queryOpenAI()
  }

  Column {
    Button(onClick = { testOpenAI() }) {
      Text(text = "aaaa")
    }
    Text("Log: $log")
//    Text("Ref: $referenceCount")
    Text("Total: $count")
    Text("HR: $refHR")
    Text("BR: $refBR")
    Text("HRV: $refHRV")
  }
}
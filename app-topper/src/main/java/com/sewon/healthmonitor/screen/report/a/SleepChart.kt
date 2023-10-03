package com.sewon.healthmonitor.screen.report.a

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SleepChart(viewModel: SleepChartViewModel = hiltViewModel()) {

  val yellowBrush = Brush.verticalGradient(
    listOf(Color(0xFFFAFF00), Color(0xFFFF8A00))
  )
  val blueBrush = Brush.verticalGradient(
    listOf(Color(0xFF03DAC5), Color(0xFF3E4F9D))
  )

  val uiState by viewModel.uiStateA.collectAsStateWithLifecycle()

  Row() {
    CircularChart(angle = uiState.sleepTime, modifier = Modifier.weight(1f), brush = yellowBrush)
    CircularChart(
      angle = uiState.sleepEfficiency, modifier = Modifier.weight(1f), brush = blueBrush
    )
    CircularChart(angle = uiState.sleepLatency, modifier = Modifier.weight(1f), brush = yellowBrush)
    CircularChart(angle = uiState.wakeupOnSleep, modifier = Modifier.weight(1f), brush = blueBrush)
  }
  Button(onClick = {
    viewModel.loadData()
  }) {
    Text("Load A")
  }

}
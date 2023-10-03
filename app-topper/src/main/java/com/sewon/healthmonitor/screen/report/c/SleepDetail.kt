package com.sewon.healthmonitor.screen.report.c

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
import com.sewon.healthmonitor.screen.report.a.CircularChart


@Composable
fun SleepDetail(viewModel: SleepDetailViewModel = hiltViewModel()) {


  val yellowBrush = Brush.verticalGradient(
    listOf(Color(0xFFFAFF00), Color(0xFFFF8A00))
  )
  val blueBrush = Brush.verticalGradient(
    listOf(Color(0xFF03DAC5), Color(0xFF3E4F9D))
  )

  val uiState by viewModel.uiStateC.collectAsStateWithLifecycle()

  Row() {
    CircularChart(angle = uiState.meanHR, modifier = Modifier.weight(1f), brush = yellowBrush)
    CircularChart(
      angle = uiState.meanBR, modifier = Modifier.weight(1f), brush = blueBrush
    )
    CircularChart(angle = uiState.SDRP, modifier = Modifier.weight(1f), brush = yellowBrush)
    CircularChart(angle = uiState.RMSSD, modifier = Modifier.weight(1f), brush = blueBrush)
  }
  Row() {
    CircularChart(
      angle = uiState.RPITriangular,
      modifier = Modifier.weight(1f),
      brush = yellowBrush
    )
    CircularChart(
      angle = uiState.lowFreq, modifier = Modifier.weight(1f), brush = blueBrush
    )
    CircularChart(angle = uiState.highFreq, modifier = Modifier.weight(1f), brush = yellowBrush)
    CircularChart(angle = uiState.lfHfRatio, modifier = Modifier.weight(1f), brush = blueBrush)
  }
  Button(onClick = {
    viewModel.loadData()
  }) {
    Text("Load data")
  }
}
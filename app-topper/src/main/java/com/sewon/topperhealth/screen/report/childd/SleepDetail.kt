package com.sewon.topperhealth.screen.report.childd

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sewon.topperhealth.screen.report.UiState
import com.sewon.topperhealth.screen.report.component.CircularChart


@Composable
fun SleepDetail(uiState: UiState) {

  val yellowBrush = Brush.verticalGradient(
    listOf(Color(0xFFFAFF00), Color(0xFFFF8A00))
  )
  val blueBrush = Brush.verticalGradient(
    listOf(Color(0xFF03DAC5), Color(0xFF3E4F9D))
  )


  Text("RPI Index")
  Row() {
    CircularChart(
      modifier = Modifier.weight(1f),
      label = "Mean HR",
      angle = uiState.meanHR, brush = yellowBrush
    )
    CircularChart(
      modifier = Modifier.weight(1f),
      label = "Mean BR",
      angle = uiState.meanBR, brush = blueBrush
    )
    CircularChart(
      modifier = Modifier.weight(1f),
      label = "SDRP",
      angle = uiState.sDRP, brush = yellowBrush
    )
    CircularChart(
      modifier = Modifier.weight(1f),
      label = "RMSSD",
      angle = uiState.rMSSD, brush = blueBrush
    )
  }
  Spacer(modifier = Modifier.height(10.dp))
  Row() {
    CircularChart(
      modifier = Modifier.weight(1f),
      label = "RPI Triangular index",
      angle = uiState.rPITriangular, brush = yellowBrush
    )
    CircularChart(
      modifier = Modifier.weight(1f),
      label = "Low Freq.",
      angle = uiState.lowFreq, brush = blueBrush
    )
    CircularChart(
      modifier = Modifier.weight(1f),
      label = "High Freq.",
      angle = uiState.highFreq, brush = yellowBrush
    )
    CircularChart(
      modifier = Modifier.weight(1f),
      label = "L/H Ratio",
      angle = uiState.lfHfRatio, brush = blueBrush
    )
  }
}
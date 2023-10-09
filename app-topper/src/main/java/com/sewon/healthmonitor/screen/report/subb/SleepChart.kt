package com.sewon.healthmonitor.screen.report.subb

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.sewon.healthmonitor.screen.report.UiState
import com.sewon.healthmonitor.screen.report.component.CircularChart

@Composable
fun SleepChart(uiState: UiState) {
  val yellowBrush = Brush.verticalGradient(
    listOf(Color(0xFFFAFF00), Color(0xFFFF8A00))
  )
  val blueBrush = Brush.verticalGradient(
    listOf(Color(0xFF03DAC5), Color(0xFF3E4F9D))
  )

  Row() {
    CircularChart(
      modifier = Modifier.weight(1f),
      label = "총수면 시간",
      angle = uiState.sleepTime,
      brush = yellowBrush
    )

    CircularChart(
      modifier = Modifier.weight(1f),
      label = "수면효율",
      angle = uiState.sleepEfficiency, brush = blueBrush
    )

    CircularChart(
      modifier = Modifier.weight(1f),
      label = "수면지연 시간",
      angle = uiState.sleepLatency,
      brush = yellowBrush
    )

    CircularChart(
      modifier = Modifier.weight(1f),
      label = "수면각성 시간",
      angle = uiState.wakeupOnSleep,
      brush = blueBrush
    )
  }
}
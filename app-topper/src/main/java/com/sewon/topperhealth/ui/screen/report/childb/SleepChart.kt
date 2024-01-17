package com.sewon.topperhealth.ui.screen.report.childb

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.sewon.topperhealth.R

import com.sewon.topperhealth.ui.screen.report.UiState
import com.sewon.topperhealth.ui.screen.report.component.CircularChart

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
      label = stringResource(R.string.total_sleep_time),
      angle = uiState.sleepTime,
      brush = yellowBrush
    )

    CircularChart(
      modifier = Modifier.weight(1f),
      label = stringResource(R.string.sleep_efficiency),
      angle = uiState.sleepEfficiency,
      brush = blueBrush
    )

    CircularChart(
      modifier = Modifier.weight(1f),
      label = stringResource(R.string.sleep_delay_time),
      angle = uiState.sleepLatency,
      brush = yellowBrush
    )

    CircularChart(
      modifier = Modifier.weight(1f),
      label = stringResource(R.string.sleep_awakening_time),
      angle = uiState.wakeupOnSleep,
      brush = blueBrush
    )
  }
}
package com.sewon.healthmonitor.screen.report.subb

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sewon.healthmonitor.screen.report.UiState
import com.sewon.healthmonitor.screen.report.subb.component.Chart1SleepStage
import com.sewon.healthmonitor.screen.report.subb.component.Chart2SleepRPI
import com.sewon.healthmonitor.screen.report.subb.component.ProgressBar


@Composable
fun SleepScore(
  uiState: UiState,
) {

  Text("PQSI 수면평가점수")
  Spacer(modifier = Modifier.height(20.dp))
  Column {
    ProgressBar(uiState.sleepRating)
  }

  Text("수면단계")
  Column {
    Chart1SleepStage(uiState.sleepStage)
  }

  Text("RPI Trachogram")
  Column {
    Chart2SleepRPI(uiState.sleepRPI)
  }
}
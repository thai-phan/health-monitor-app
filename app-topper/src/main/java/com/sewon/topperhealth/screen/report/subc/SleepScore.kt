package com.sewon.topperhealth.screen.report.subc

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sewon.topperhealth.screen.report.UiState
import com.sewon.topperhealth.screen.report.subc.component.Chart1SleepStage
import com.sewon.topperhealth.screen.report.subc.component.Chart2SleepRPI
import com.sewon.topperhealth.screen.report.subc.component.ProgressBar


@Composable
fun SleepScore(
  uiState: UiState,
) {

  Text("수면품질지표(SQI)")
  Spacer(modifier = Modifier.height(20.dp))
  Column {
    ProgressBar(uiState.sleepRating)
  }

  Text("수면단계")
  Column {
    Chart1SleepStage(uiState.sleepStage)
  }

  Text("RPI Tarchogram")
  Column {
    Chart2SleepRPI(uiState.sleepRPI)
  }
}
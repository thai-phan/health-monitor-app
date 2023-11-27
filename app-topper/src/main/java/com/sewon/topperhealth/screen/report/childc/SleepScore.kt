package com.sewon.topperhealth.screen.report.childc

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.report.UiState
import com.sewon.topperhealth.screen.report.childc.component.Chart1SleepStage
import com.sewon.topperhealth.screen.report.childc.component.ProgressBar


@Composable
fun SleepScore(
  uiState: UiState,
) {

  Text(stringResource(R.string.sleep_quality_index))
  Spacer(modifier = Modifier.height(20.dp))
  Column {
    Text(uiState.sleepRating.toString())
    ProgressBar(uiState.sleepRating)
  }

  Text(stringResource(R.string.sleep_stage))
  Column {
    Chart1SleepStage(uiState.sleepStage)
  }

//  Text("RPI Tachogram")
//  Column {
//    Chart2SleepRPI(uiState.sleepRPI)
//  }
}
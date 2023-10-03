package com.sewon.healthmonitor.screen.report.b

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


@Composable
fun SleepScore(
  viewModel: SleepScoreViewModel = hiltViewModel()
) {

  val uiState by viewModel.uiStateB.collectAsStateWithLifecycle()


  Text("PQSI 수면평가점수")
  Spacer(modifier = Modifier.height(20.dp))
  Column {
    ProgressBar(value = uiState.sleepRating)
  }

  Column {
    Chart1SleepStage(uiState.sleepStage)
  }

  Column {
    Chart2SleepRPI(uiState.sleepRPI)
  }

  Button(onClick = {
    viewModel.loadData()
  }) {
    Text("Load B")
  }

//  Chart


}
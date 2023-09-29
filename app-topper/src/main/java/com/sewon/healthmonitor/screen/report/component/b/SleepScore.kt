package com.sewon.healthmonitor.screen.report.component.b

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun SleepScore(
  viewModel: SleepScoreViewModel = hiltViewModel()
) {


  Text("PQSI 수면평가점수")
  Spacer(modifier = Modifier.height(20.dp))
  Column {
    ProgressBar()

    ProgressBar()

    ProgressBar()

    ProgressBar()
  }

  Column {
    ChartSleepStage()
  }

  Column {
    ChartSleepStage()
  }

  Button(onClick = {
    viewModel.loadData()
  }) {
    Text("Load data")
  }

//  Chart


}
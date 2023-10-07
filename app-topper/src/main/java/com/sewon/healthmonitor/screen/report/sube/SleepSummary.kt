package com.sewon.healthmonitor.screen.report.sube

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sewon.healthmonitor.screen.report.UiState


@Composable
fun SleepSummary(uiState: UiState) {
  Row() {
    Column(modifier = Modifier.weight(1f)) {
      Text("자율 신경계 밸런스")
      Chart1NervousSystem(uiState.nervousScore)

    }

    Column(modifier = Modifier.weight(1f)) {
      Text("스트레스 점수")
      Chart2StressScore(uiState.stressScore)
    }
  }
}
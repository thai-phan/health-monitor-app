package com.sewon.healthmonitor.screen.report.sube

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.sewon.healthmonitor.screen.report.UiState


@Composable
fun SleepSummary(uiState: UiState) {
  Text("자율 신경계 밸런스")
  Chart1NervousSystem(uiState.nervousScore)

  Text("스트레스 점수")
  Chart2StressScore(uiState.stressScore)
}
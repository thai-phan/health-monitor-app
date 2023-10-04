package com.sewon.healthmonitor.screen.report.subd

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sewon.healthmonitor.screen.report.UiState


@Composable
fun SleepSummary(uiState: UiState) {
  Text("자율 신경계 밸런스")
  Chart1NervousSystem()

  Text("스트레스 점수")
  Chart2StressScore()
}
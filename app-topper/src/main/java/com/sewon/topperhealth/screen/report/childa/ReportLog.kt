package com.sewon.topperhealth.screen.report.childa

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.sewon.topperhealth.screen.report.UiState

@Composable
fun ReportLog(uiState: UiState) {
  Text(uiState.totalRecord.toString())
  Text(uiState.refHRV.toString())
  Text(uiState.refHR.toString())
  Text(uiState.refBR.toString())
}
package com.sewon.topperhealth.screen.report.childa

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.sewon.topperhealth.screen.report.UiState

@Composable
fun ReportLog(uiState: UiState) {
  Text("Total: ${uiState.totalRecord}")
  Text("HR: ${uiState.refHR}")
  Text("BR: ${uiState.refBR}")
  Text("HRV: ${uiState.refHRV}")
}
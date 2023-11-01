package com.sewon.topperhealth.screen.report.sube

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.report.UiState


@Composable
fun SleepSummary(uiState: UiState) {
  Row() {
    Column(modifier = Modifier.weight(1f)) {
      Text(stringResource(R.string.nervous_balance))
      Chart1NervousSystem(uiState.nervousScore)

    }

    Column(modifier = Modifier.weight(1f)) {
      Text(stringResource(R.string.stress_score))
      Chart2StressScore(uiState.stressScore)
    }
  }
}
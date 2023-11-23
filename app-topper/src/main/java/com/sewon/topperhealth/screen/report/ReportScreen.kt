package com.sewon.topperhealth.screen.report


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.a0common.theme.topperTypography
import com.sewon.topperhealth.screen.report.suba.SessionSelection
import com.sewon.topperhealth.screen.report.subb.SleepChart
import com.sewon.topperhealth.screen.report.subc.SleepScore
import com.sewon.topperhealth.screen.report.subd.SleepDetail
import com.sewon.topperhealth.screen.report.sube.SleepSummary

@Composable
fun ReportScreen(
  modifier: Modifier,
  viewModel: ReportViewModel = hiltViewModel(),
) {

  val uiState by viewModel.uiState.collectAsStateWithLifecycle()


  fun selectSessionReport(id: Int) {
    viewModel.showSessionReport(id)
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(
        start = 20.dp, top = 20.dp, end = 20.dp, bottom = 10.dp
      )
  ) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
      Text(stringResource(R.string.report_sleep_report), style = topperTypography.headlineSmall)
    }
    if (uiState.sessionList.isNotEmpty()) {
      SessionSelection(uiState.sessionList, selectReportSession = {
        selectSessionReport(it)
      })
      Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
      ) {
        Text(uiState.totalRecord.toString())
        SleepChart(uiState)
        SleepScore(uiState)
//        SleepDetail(uiState)
//        SleepSummary(uiState)

      }
    } else {
      Text("Sleep Session Empty")
    }
  }
}


package com.sewon.topperhealth.screen.report


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sewon.topperhealth.R
import com.sewon.topperhealth.data.DataStoreManager
import com.sewon.topperhealth.screen.a0common.theme.topperTypography
import com.sewon.topperhealth.screen.report.childa.ReportLog
import com.sewon.topperhealth.screen.report.childa.SessionSelection
import com.sewon.topperhealth.screen.report.childb.SleepChart
import com.sewon.topperhealth.screen.report.childc.SleepScore

@Composable
fun ReportScreen(
  modifier: Modifier,
  viewModel: ReportViewModel = hiltViewModel(),
) {
  val context = LocalContext.current
  val dataStore = DataStoreManager(context)

  val isLogShowed by remember { dataStore.isLogShowed }.collectAsState(initial = false)

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
      Spacer(Modifier.height(10.dp))
      Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
      ) {
        if (isLogShowed) {
          ReportLog(uiState)
        }

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


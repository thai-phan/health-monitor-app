package com.sewon.healthmonitor.screen.report


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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sewon.healthmonitor.screen.report.subb.SleepChart
import com.sewon.healthmonitor.screen.report.suba.SessionSelection
import com.sewon.healthmonitor.screen.report.subc.SleepScore
import com.sewon.healthmonitor.screen.report.subd.SleepDetail
import com.sewon.healthmonitor.screen.report.sube.SleepSummary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
  modifier: Modifier,
  viewModel: ReportViewModel = hiltViewModel(),
) {

  val context = LocalContext.current
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//    val regularClass = RegularClass(context)
//    regularClass.findResource()

  fun selectSessionReport(id: Int) {
    viewModel.showSessionReport(id)
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(
        start = 20.dp, top = 20.dp, end = 20.dp, bottom = 10.dp
      )
      .verticalScroll(rememberScrollState()),
//    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
      Text("수면 리포트", fontSize = 24.sp)
    }
    if (uiState.sessionList.isNotEmpty()) {
      SessionSelection(uiState.sessionList, selectReportSession = {
        selectSessionReport(it)
      })

      SleepChart(uiState)
      Spacer(modifier = Modifier.height(10.dp))

      SleepScore(uiState)
      Spacer(modifier = Modifier.height(10.dp))

      SleepDetail(uiState)
      Spacer(modifier = Modifier.height(10.dp))

      SleepSummary(uiState)
    } else {
      Text("Sleep Session Empty")
    }
  }
}


@Preview
@Composable
fun PreviewReport() {
  ReportScreen(Modifier)
}
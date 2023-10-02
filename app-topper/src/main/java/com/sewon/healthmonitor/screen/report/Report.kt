package com.sewon.healthmonitor.screen.report


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sewon.healthmonitor.MainActivity
import com.sewon.healthmonitor.screen.report.component.a.SleepChart
import com.sewon.healthmonitor.screen.report.component.b.SleepScore

@Composable
fun Report(
  modifier: Modifier,
  viewModel: ReportViewModel = hiltViewModel(),
) {

  val context = LocalContext.current
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//    val regularClass = RegularClass(context)
//    regularClass.findResource()

  val yellowBrush = Brush.verticalGradient(
    listOf(Color(0xFFFAFF00), Color(0xFFFF8A00))
  )
  val blueBrush = Brush.verticalGradient(
    listOf(Color(0xFF03DAC5), Color(0xFF3E4F9D))
  )


  fun buttonTest() {
    MainActivity.bleHandleService.loadData()
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
    Text("수면 리포트", fontSize = 24.sp)
    Button(onClick = { buttonTest() }) {
      Text("Test")
    }
    SleepChart()
    SleepScore()


  }
}


@Preview
@Composable
fun PreviewReport() {
  Report(Modifier)
}
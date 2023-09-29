package com.sewon.healthmonitor.screen.report.component.b

import androidx.compose.runtime.Composable
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
fun ChartSleepStage() {

  val chartEntryModel = entryModelOf(4f, 12f, 8f, 16f)

  Chart(
    chart = lineChart(),
    model = chartEntryModel,
    startAxis = startAxis(),
    bottomAxis = bottomAxis(),
  )

}

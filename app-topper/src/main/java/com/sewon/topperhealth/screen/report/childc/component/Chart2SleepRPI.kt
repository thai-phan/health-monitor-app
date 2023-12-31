package com.sewon.topperhealth.screen.report.childc.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.chart.copy
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.sewon.topperhealth.screen.report.component.rememberChartStyle

@Composable
fun Chart2SleepRPI(data: List<Float>) {

  val lineChartColors = listOf(Color(0xFF00FD55))
  ProvideChartStyle(rememberChartStyle(lineChartColors)) {
    val chartEntryModel = entryModelOf(*data.map { it }.toTypedArray())
    val defaultLines = currentChartStyle.lineChart.lines
    val pointConnector = DefaultPointConnector(cubicStrength = 0f)
    val lineChart = lineChart(
      remember(defaultLines) {
        defaultLines.map { defaultLine -> defaultLine.copy(pointConnector = pointConnector) }
      },
    )
    Chart(
      chart = lineChart,
      model = chartEntryModel,
      startAxis = rememberStartAxis(),
      bottomAxis = rememberBottomAxis(),
    )
  }

}

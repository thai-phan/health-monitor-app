package com.sewon.healthmonitor.screen.report.sube

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.sewon.healthmonitor.screen.report.subc.component.rememberChartStyle


@Composable
fun Chart1NervousSystem(data: List<Float>) {
  val lineChartColors = listOf(Color(0xFF287EFF))
  ProvideChartStyle(rememberChartStyle(lineChartColors)) {
    val chartEntryModel = entryModelOf(*data.map { it }.toTypedArray())
    val defaultLines = currentChartStyle.lineChart.lines
    val pointConnector = DefaultPointConnector(cubicStrength = 0f)
    val chart = columnChart()
    Chart(
      chart = chart,
      model = chartEntryModel,
      startAxis = rememberStartAxis(),
      bottomAxis = rememberBottomAxis(),
    )
  }
}
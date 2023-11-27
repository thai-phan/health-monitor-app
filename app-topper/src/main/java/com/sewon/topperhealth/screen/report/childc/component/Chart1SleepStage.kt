package com.sewon.topperhealth.screen.report.childc.component

import android.graphics.Typeface
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.legend.horizontalLegend
import com.patrykandpatrick.vico.compose.legend.legendItem
import com.patrykandpatrick.vico.compose.legend.verticalLegend
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition.Vertical.Start
import com.patrykandpatrick.vico.core.axis.formatter.DefaultAxisValueFormatter
import com.patrykandpatrick.vico.core.axis.formatter.PercentageFormatAxisValueFormatter
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.chart.copy
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.legend.HorizontalLegend
import com.patrykandpatrick.vico.core.legend.Legend
import com.patrykandpatrick.vico.core.legend.VerticalLegend
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.report.component.rememberChartStyle

@Composable
fun Chart1SleepStage(data: List<Float>) {
  val lineChartColors = listOf(Color(0xFF287EFF))
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
      modifier = Modifier.height(250.dp),
      legend = rememberLegend(),
      chart = lineChart,
      model = chartEntryModel,
      startAxis = rememberStartAxis(
        valueFormatter = startAxisValueFormatter,
        itemPlacer = startAxisItemPlacer
      ),

      bottomAxis = rememberBottomAxis(),
    )
  }
}

private val startAxisValueFormatter = DefaultAxisValueFormatter<Start>()
private val startAxisItemPlacer = AxisItemPlacer.Vertical.default(5)

private val legendItemLabelTextSize = 12.sp
private val legendItemIconSize = 8.dp
private val legendItemIconPaddingValue = 10.dp
private val legendItemSpacing = 4.dp
private val legendTopPaddingValue = 8.dp
private val legendPadding = dimensionsOf(top = legendTopPaddingValue)

val mapRem = mapOf(
  "WAKE" to 4,
  "REM" to 3,
  "N1" to 2,
  "N2" to 1,
  "N3" to 0
)

@Composable
private fun rememberLegend(): Legend {
  val textComponent = textComponent(
    color = currentChartStyle.axis.axisLabelColor,
    textSize = legendItemLabelTextSize,
    typeface = Typeface.MONOSPACE,
  )
  return verticalLegend(
    items = mapRem.map {
      legendItem(
        icon = shapeComponent(Shapes.pillShape, currentChartStyle.axis.axisLabelColor),
        label = textComponent,
        labelText = "${it.value}: ${it.key}",
      )
    },
    iconSize = legendItemIconSize,
    iconPadding = legendItemIconPaddingValue,
    spacing = legendItemSpacing,
    padding = legendPadding,
  )
}




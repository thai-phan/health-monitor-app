/*
 * Copyright 2023 by Patryk Goworowski and Patrick Michalik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sewon.healthmonitor.screen.report.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.DefaultColors
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders

@Composable
internal fun rememberChartStyle(
  columnChartColors: List<Color>,
  lineChartColors: List<Color>
): ChartStyle {
  val isSystemInDarkTheme = isSystemInDarkTheme()
  return remember(columnChartColors, lineChartColors, isSystemInDarkTheme) {
    val defaultColors = if (isSystemInDarkTheme) DefaultColors.Dark else DefaultColors.Light
    ChartStyle(
      axis = ChartStyle.Axis(
        axisLabelColor = Color(0xFF03DAC5),
        axisGuidelineColor = Color(defaultColors.axisGuidelineColor),
        axisLineColor = Color.Cyan,
      ),
      columnChart = ChartStyle.ColumnChart(
        columnChartColors.map { columnChartColor ->
          LineComponent(
            columnChartColor.toArgb(),
            DefaultDimens.COLUMN_WIDTH,
            Shapes.roundedCornerShape(DefaultDimens.COLUMN_ROUNDNESS_PERCENT),
          )
        },
      ),
      lineChart = ChartStyle.LineChart(
        lineChartColors.map { lineChartColor ->
          LineChart.LineSpec(
            lineColor = lineChartColor.toArgb(),
            lineBackgroundShader = DynamicShaders.fromBrush(
              Brush.verticalGradient(
                listOf(
                  lineChartColor.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                  lineChartColor.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_END),
                ),
              ),
            ),
          )
        },
      ),
      marker = ChartStyle.Marker(),
      elevationOverlayColor = Color(defaultColors.elevationOverlayColor),
    )
  }
}

@Composable
internal fun rememberChartStyle(chartColors: List<Color>) =
  rememberChartStyle(columnChartColors = chartColors, lineChartColors = chartColors)

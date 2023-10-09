package com.sewon.healthmonitor.screen.report.sube

import androidx.compose.runtime.Composable
import com.sewon.healthmonitor.screen.report.component.CircularChart


@Composable
fun Chart2StressScore(stressScore: Float) {
  CircularChart(label = "스트레스 점수", angle = stressScore)
}
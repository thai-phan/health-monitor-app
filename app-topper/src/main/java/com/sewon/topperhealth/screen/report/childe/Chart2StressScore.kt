package com.sewon.topperhealth.screen.report.childe

import androidx.compose.runtime.Composable
import com.sewon.topperhealth.screen.report.component.CircularChart


@Composable
fun Chart2StressScore(stressScore: Float) {
  CircularChart(label = "스트레스 점수", angle = stressScore)
}
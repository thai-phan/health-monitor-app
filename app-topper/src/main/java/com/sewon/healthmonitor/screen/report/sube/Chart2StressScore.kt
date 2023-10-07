package com.sewon.healthmonitor.screen.report.sube

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sewon.healthmonitor.screen.report.component.CircularChart


@Composable
fun Chart2StressScore(stressScore: Float) {
  CircularChart(angle = stressScore)
}
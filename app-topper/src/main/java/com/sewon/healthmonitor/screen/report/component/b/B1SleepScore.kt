package com.sewon.healthmonitor.screen.report.component.b

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun B1SleepScore() {


  Text("PQSI 수면평가점수")
  Spacer(modifier = Modifier.height(20.dp))

  Progressbar()

  Progressbar()

  Progressbar()

  Progressbar()
}
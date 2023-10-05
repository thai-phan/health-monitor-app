package com.sewon.healthmonitor.screen.activity.sub

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sewon.healthmonitor.common.timepicker.TimeRangePicker
import java.sql.Time


@Composable
fun TimeSelection(startTime: TimeRangePicker.Time, endTime: TimeRangePicker.Time) {


  Row(modifier = Modifier.fillMaxWidth()) {
    Column(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text("취침시간")
      Text(startTime.toString(), fontWeight = FontWeight.Bold, fontSize = 30.sp)
    }
    Column(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text("기상시간")
      Text(endTime.toString(), fontWeight = FontWeight.Bold, fontSize = 30.sp)
    }
  }
}
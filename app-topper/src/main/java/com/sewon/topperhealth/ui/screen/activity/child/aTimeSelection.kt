package com.sewon.topperhealth.ui.screen.activity.child

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sewon.topperhealth.R
import com.sewon.topperhealth.ui.common.timepicker.TimeRangePicker
import com.sewon.topperhealth.ui.theme.topperTypography


@Composable
fun TimeSelection(startTime: TimeRangePicker.Time, endTime: TimeRangePicker.Time) {
  Row(modifier = Modifier.fillMaxWidth()) {
    Column(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(stringResource(R.string.bedtime))
      Text(startTime.toString(), style = topperTypography.headlineLarge)
    }
    Column(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(stringResource(R.string.wakeup_time))
      Text(endTime.toString(), style = topperTypography.headlineLarge)
    }
  }
}
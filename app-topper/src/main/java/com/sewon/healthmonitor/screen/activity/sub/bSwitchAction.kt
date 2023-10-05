package com.sewon.healthmonitor.screen.activity.sub

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sewon.healthmonitor.common.timepicker.TimeRangePicker

@Composable
fun SwitchAction() {

//  val switchColors: SwitchColors = SwitchDefaults.colors(
//    checkedThumbColor = checkedThumbColor,
//    checkedTrackColor = checkedTrackColor,
//    checkedBorderColor = checkedBorderColor,
//    uncheckedThumbColor = uncheckedThumbColor,
//    uncheckedTrackColor = uncheckedTrackColor,
//    uncheckedBorderColor = uncheckedBorderColor,
//  )

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text("수면유도에너지")
    Switch(checked = true, onCheckedChange = {

    })
  }

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text("수면유도사운드")
    Switch(checked = true, onCheckedChange = {

    })
  }
}
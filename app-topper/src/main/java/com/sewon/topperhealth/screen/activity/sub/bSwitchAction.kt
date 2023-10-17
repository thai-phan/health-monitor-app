package com.sewon.topperhealth.screen.activity.sub

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sewon.topperhealth.common.theme.checkedBorderColor
import com.sewon.topperhealth.common.theme.checkedThumbColor
import com.sewon.topperhealth.common.theme.checkedTrackColor
import com.sewon.topperhealth.common.theme.uncheckedBorderColor
import com.sewon.topperhealth.common.theme.uncheckedThumbColor
import com.sewon.topperhealth.common.theme.uncheckedTrackColor

@Composable
fun SwitchAction(toggleConnectDevice: Boolean,onToggleAlarmSound: () -> Unit, toggleAlarmSound: Boolean, onToggleConnectToDevice: () -> Unit) {

  val switchColors: SwitchColors = SwitchDefaults.colors(
    checkedThumbColor = checkedThumbColor,
    checkedTrackColor = checkedTrackColor,
    checkedBorderColor = checkedBorderColor,
    uncheckedThumbColor = uncheckedThumbColor,
    uncheckedTrackColor = uncheckedTrackColor,
    uncheckedBorderColor = uncheckedBorderColor,
  )

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text("수면유도에너지")
    Switch(checked = toggleConnectDevice,
      colors = switchColors,
      onCheckedChange = {
        onToggleConnectToDevice()
      })
  }

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text("수면유도사운드")
    Switch(checked = toggleAlarmSound,
      colors = switchColors,
      onCheckedChange = {
        onToggleAlarmSound()
      })
  }
}
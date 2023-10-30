package com.sewon.topperhealth.screen.a0common.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sewon.topperhealth.service.bluetooth.util.Connected

// SwitchColors color
// ColorSchemeKeyTokens.OnPrimary
val checkedThumbColor = Color(0xFF09897D)

// ColorSchemeKeyTokens.Primary
val checkedTrackColor = Color(0xFF03DAC5)

// Color.Transparent
val checkedBorderColor = Color(0xFF03DAC5)

// ColorSchemeKeyTokens.Outline
val uncheckedThumbColor = Color(0xFF938F99)

// ColorSchemeKeyTokens.SurfaceVariant
val uncheckedTrackColor = Color(0xFF36343B)

// ColorSchemeKeyTokens.Outline
val uncheckedBorderColor = Color(0xFF938F99)

@Composable
fun CustomSwitch(
  checked: Boolean,
  onCheckedChange: ((Boolean) -> Unit)?,
  modifier: Modifier = Modifier,
  thumbContent: (@Composable () -> Unit)? = null,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  val switchColors: SwitchColors = SwitchDefaults.colors(
    checkedThumbColor = checkedThumbColor,
    checkedTrackColor = checkedTrackColor,
    checkedBorderColor = checkedBorderColor,
    uncheckedThumbColor = uncheckedThumbColor,
    uncheckedTrackColor = uncheckedTrackColor,
    uncheckedBorderColor = uncheckedBorderColor,
  )
  return Switch(
    checked,
    onCheckedChange,
    modifier,
    thumbContent,
    enabled,
    switchColors,
    interactionSource,
  )
}
package com.sewon.topperhealth.screen.activity.child

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddAlert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sewon.topperhealth.R

@Composable
fun ButtonAction(
  isStarted: Boolean,
  isAlarm: Boolean,
  startSleep: () -> Unit,
  cancelSleep: () -> Unit,
  stopAlarm: () -> Unit
) {
  val textStyle = TextStyle(
    fontSize = 24.sp,
    lineHeight = 24.sp,
    fontFamily = FontFamily(Font(R.font.suite_regular)),
    fontWeight = FontWeight(900),
    color = Color(0xFF002723),
    textAlign = TextAlign.Center,
  )

  val isEnable = 1

  Column(
    modifier = Modifier
      .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {

    if (isEnable == 0) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .background(color = Color(0xFFF4BF6E)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(imageVector = Icons.Rounded.AddAlert, contentDescription = "")
        Text("건강이상감지", style = textStyle)
      }
    }

    if (isEnable == 0) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .background(color = Color(0xFFFF1144)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(imageVector = Icons.Rounded.AddAlert, contentDescription = "")
        Text("건강이상감지", style = textStyle)
      }
    }
    if (!isStarted) {
      Button(colors = ButtonDefaults.buttonColors(Color(0xFF03DAC5)),
        shape = RoundedCornerShape(100.dp),
        modifier = Modifier
          .width(280.dp)
          .height(56.dp),
        onClick = { startSleep() }) {
        Text(stringResource(R.string.activity), style = textStyle)
      }
    } else {
      Button(colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
        shape = RoundedCornerShape(100.dp),
        modifier = Modifier
          .width(280.dp)
          .height(56.dp),
        onClick = { cancelSleep() }) {
        Text(stringResource(R.string.wakeup_button), style = textStyle)
      }
    }
    if (isAlarm) {
      Spacer(Modifier.height(10.dp))
      Button(colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
        shape = RoundedCornerShape(100.dp),
        modifier = Modifier
          .width(200.dp)
          .height(56.dp),
        onClick = { stopAlarm() }) {
        Text(stringResource(R.string.stop_alarm), style = textStyle)
      }
    }
  }
}
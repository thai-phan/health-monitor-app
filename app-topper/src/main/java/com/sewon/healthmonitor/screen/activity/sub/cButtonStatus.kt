package com.sewon.healthmonitor.screen.activity.sub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddAlert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sewon.healthmonitor.R

@Composable
fun ButtonAction(
  isStarted: MutableState<Boolean>,
  startSleep: () -> Unit,
  cancelSleep: () -> Unit
) {
  val textStyle = TextStyle(
    fontSize = 24.sp,
    lineHeight = 24.sp,
    fontFamily = FontFamily(Font(R.font.suite_regular)),
    fontWeight = FontWeight(900),
    color = Color(0xFF002723),
    textAlign = TextAlign.Center,
  )


  var isEnable = 1

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .verticalScroll(rememberScrollState()),
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
    if (!isStarted.value) {
      Button(colors = ButtonDefaults.buttonColors(Color(0xFF03DAC5)),
        shape = RoundedCornerShape(size = 100.dp),
        modifier = Modifier
          .width(280.dp)
          .height(56.dp),
        onClick = { startSleep() }) {
        Text(
          "수면시작", style = textStyle
        )
      }
    } else {
      Button(colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
        shape = RoundedCornerShape(size = 100.dp),
        modifier = Modifier
          .width(280.dp)
          .height(56.dp),
        onClick = { cancelSleep() }) {
        Text(
          "기상", style = textStyle
        )
      }
    }


  }
}
package com.sewon.healthmonitor.screen.activity

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.bluetooth.BluetoothManager
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sewon.healthmonitor.MainActivity
import com.sewon.healthmonitor.MainActivity.Companion.alarmManager
import com.sewon.healthmonitor.MainActivity.Companion.alarmPendingIntent
import com.sewon.healthmonitor.R
import com.sewon.healthmonitor.common.theme.checkedBorderColor
import com.sewon.healthmonitor.common.theme.checkedThumbColor
import com.sewon.healthmonitor.common.theme.checkedTrackColor
import com.sewon.healthmonitor.common.theme.uncheckedBorderColor
import com.sewon.healthmonitor.common.theme.uncheckedThumbColor
import com.sewon.healthmonitor.common.theme.uncheckedTrackColor
import com.sewon.healthmonitor.common.timepicker.TimeRangePicker
import com.sewon.healthmonitor.screen.activity.component.CircularTimePicker
import com.sewon.healthmonitor.service.alarm.AlarmReceiver
import com.sewon.healthmonitor.service.alarm.AlarmReceiver.Companion.ringtone
import com.sewon.healthmonitor.service.ble.SerialSocket
import timber.log.Timber
import java.util.Calendar

@SuppressLint("RememberReturnType")
@Composable
fun SleepActivity(
  modifier: Modifier = Modifier,
  navController: NavHostController = rememberNavController(),
  viewModel: ViewModelSleepActivity = hiltViewModel(),
  deviceAddress: String = ""
) {
  val context = LocalContext.current
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  val start = TimeRangePicker.Time(0, 0)
  val end = TimeRangePicker.Time(5, 30)

  val startTime = remember { mutableStateOf(start) }
  val endTime = remember { mutableStateOf(end) }


  fun connectToSensor() {
    try {
      val adapter = context.getSystemService<BluetoothManager>()?.adapter
      val device = adapter?.getRemoteDevice(deviceAddress)
      val socket = device?.let {
        SerialSocket(context, it)
      }
      if (socket != null) {
        MainActivity.bleHandleService.connect(socket)
      }
    } catch (exception: IllegalArgumentException) {
      Timber.tag("Timber").w(exception)
    }
  }


  fun startSleep() {
    connectToSensor()

//    navController.navigate(MainDestinations.REPORT_ROUTE)
//    MainActivity.serialService.
  }

  fun cancelSleep() {
//      AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//      Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
//      PendingIntent pendingIntent = PendingIntent.getBroadcast(
//          getApplicationContext(), 1, myIntent, PendingIntent.FLAG_IMMUTABLE);

  }

  fun stopSound() {

  }

  fun disconnect() {
//    val a = AlarmManager()
//    val list = listOf("1", "2", "3", "4.0", "5.0", "6.0")
//    MainActivity.bleHandleService.updateDatabase(list)
//    MainActivity.bleHandleService.disconnect()
//    navController.navigate(AppDestinations.MAIN_ROUTE)
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 30.dp, vertical = 20.dp)
  ) {
    Text("수면시간 체크", fontWeight = FontWeight.Bold, fontSize = 24.sp)

//    Row {
//      Button(onClick = { cancelSleep() }) {
//        Text("cancel")
//      }
//
//      Button(onClick = { stopSound() }) {
//        Text("stop")
//      }
//    }


    Spacer(modifier = Modifier.height(10.dp))
    CircularTimePicker(startTimeState = startTime, endTimeState = endTime)
    Spacer(modifier = Modifier.height(10.dp))
    Row(modifier = Modifier.fillMaxWidth()) {
      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text("취침시간")
        Text(startTime.value.toString(), fontWeight = FontWeight.Bold, fontSize = 30.sp)
//                Text("PM")
      }
      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text("기상시간")
        Text(endTime.value.toString(), fontWeight = FontWeight.Bold, fontSize = 30.sp)
//                Text("AM")
      }
    }

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
      Switch(checked = true, colors = switchColors, onCheckedChange = {})
    }

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text("수면유도사운드")
      Switch(checked = true, colors = switchColors, onCheckedChange = {})
    }
    Text(uiState.status3)

    val textStyle = TextStyle(
      fontSize = 24.sp,
      lineHeight = 24.sp,
      fontFamily = FontFamily(Font(R.font.suite_regular)),
      fontWeight = FontWeight(900),
      color = Color(0xFF002723),
      textAlign = TextAlign.Center,
    )
    Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {

//      Row(
//        modifier = Modifier
//          .fillMaxWidth()
//          .height(48.dp)
//          .background(color = Color(0xFFF4BF6E)),
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically
//      ) {
//        Icon(imageVector = Icons.Rounded.AddAlert, contentDescription = "")
//        Text("건강이상감지", style = textStyle)
//      }
//
//      Row(
//        modifier = Modifier
//          .fillMaxWidth()
//          .height(48.dp)
//          .background(color = Color(0xFFFF1144)),
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically
//      ) {
//        Icon(imageVector = Icons.Rounded.AddAlert, contentDescription = "")
//        Text("건강이상감지", style = textStyle)
//      }

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

      Button(
        colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
        onClick = { disconnect() }
      ) {
        Text(
          text = "연결됨"
        )
      }
//      Button(colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
//        shape = RoundedCornerShape(size = 100.dp),
//        modifier = Modifier
//          .width(280.dp)
//          .height(56.dp),
//        onClick = { }) {
//        Text(
//          "기상", style = textStyle
//        )
//      }
    }
  }
}

@Preview
@Composable
fun PreviewSleepActivity() {
  SleepActivity()
}
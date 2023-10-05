package com.sewon.healthmonitor.screen.activity

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sewon.healthmonitor.MainActivity
import com.sewon.healthmonitor.common.MainDestinations
import com.sewon.healthmonitor.common.timepicker.TimeRangePicker
import com.sewon.healthmonitor.screen.activity.component.CircularTimePicker
import com.sewon.healthmonitor.screen.activity.sub.ButtonAction

import com.sewon.healthmonitor.screen.activity.sub.SwitchAction
import com.sewon.healthmonitor.screen.activity.sub.TimeSelection
import com.sewon.healthmonitor.service.ble.SerialSocket
import timber.log.Timber

@SuppressLint("RememberReturnType")
@Composable
fun SleepActivity(
  modifier: Modifier = Modifier,
  navController: NavHostController = rememberNavController(),
  viewModel: ActivityViewModel = hiltViewModel(),
  deviceAddress: String = ""
) {
  val context = LocalContext.current
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  val log by MainActivity.bleDataListener.log

  val start = TimeRangePicker.Time(0, 0)
  val end = TimeRangePicker.Time(5, 30)
  val duration = TimeRangePicker.TimeDuration(start, end)

  val startTime = remember { mutableStateOf(start) }
  val endTime = remember { mutableStateOf(end) }
  val durationState = remember { mutableStateOf(duration) }

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

  val isStarted = remember { mutableStateOf(false) }

  fun startSleep() {
    isStarted.value = true
    viewModel.createSession(startTime.value, endTime.value, durationState.value)
    connectToSensor()
//    navController.navigate(MainDestinations.REPORT_ROUTE)
//    MainActivity.serialService.
  }

  fun cancelSleep() {
    isStarted.value = false
    MainActivity.bleHandleService.disconnect()

//      AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//      Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
//      PendingIntent pendingIntent = PendingIntent.getBroadcast(
//          getApplicationContext(), 1, myIntent, PendingIntent.FLAG_IMMUTABLE);
  }

  fun createSleepSession() {
  }


  fun redirectReportPage() {
    navController.navigate(MainDestinations.REPORT_ROUTE)
//    val a = AlarmManager()
//    val list = listOf("1", "2", "3", "4.0", "5.0", "6.0")
//    MainActivity.bleHandleService.updateDatabase(list)
//    MainActivity.bleHandleService.disconnect()

  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 30.dp, vertical = 20.dp)
  ) {
    Text("수면시간 체크", fontWeight = FontWeight.Bold, fontSize = 24.sp)

    Text(log)

    Spacer(modifier = Modifier.height(10.dp))

    CircularTimePicker(startTime, endTime, durationState)

    Spacer(modifier = Modifier.height(10.dp))

    TimeSelection(startTime.value, endTime.value)

    SwitchAction()

    ButtonAction(isStarted, startSleep = { startSleep() }, cancelSleep = { cancelSleep() })

    Button(colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
      onClick = { redirectReportPage() }) {
      Text(
        text = "연결됨"
      )
    }
  }
}

@Preview
@Composable
fun PreviewSleepActivity() {
  SleepActivity()
}
package com.sewon.healthmonitor.screen.activity

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.icu.util.GregorianCalendar
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
import androidx.compose.runtime.setValue
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
import com.sewon.healthmonitor.service.alarm.AlarmReceiver
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

  fun composeConnectToSensor() {
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

  var isStarted by remember { mutableStateOf(false) }
  var isAlarm by MainActivity.bleDataListener.isAlarm

  fun startSleep() {
//    Toast.makeText(context, "ALARM ON", Toast.LENGTH_SHORT).show()

    isStarted = true

    val startTimeCalendar = GregorianCalendar.getInstance()
    startTimeCalendar.set(GregorianCalendar.HOUR_OF_DAY, startTime.value.hour)
    startTimeCalendar.set(GregorianCalendar.MINUTE, startTime.value.minute)
    startTimeCalendar.set(GregorianCalendar.SECOND, 0)
    val endTimeCalendar = startTimeCalendar.clone() as android.icu.util.Calendar?
    endTimeCalendar!!.add(GregorianCalendar.HOUR_OF_DAY, durationState.value.hour)
    endTimeCalendar.add(GregorianCalendar.MINUTE, durationState.value.minute)

    viewModel.createSession(startTimeCalendar, endTimeCalendar)
    composeConnectToSensor()

    val alarmIntent = Intent(context, AlarmReceiver::class.java)
    MainActivity.alarmPendingIntent =
      PendingIntent.getBroadcast(context, 1234, alarmIntent, PendingIntent.FLAG_MUTABLE)
//    val calendar = GregorianCalendar.getInstance()
//    calendar[GregorianCalendar.SECOND] = calendar[GregorianCalendar.SECOND] + 30
//    Timber.tag("Timber").d(endTimeCalendar.time.toString())
//    Timber.tag("Timber").d(calendar.time.toString())
//    //          ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), DAY, pendingIntent);
//    MainActivity.alarmManager.set(
//      AlarmManager.RTC_WAKEUP,
//      calendar.timeInMillis,
//      MainActivity.alarmPendingIntent
//    )
  }

  fun cancelSleep() {
    isStarted = false

    MainActivity.alarmManager.cancel(MainActivity.alarmPendingIntent)

    viewModel.updateCurrentSessionEndTime()
    MainActivity.bleHandleService.disconnectBluetoothSocket()
  }

  fun stopAlarm() {
    MainActivity.bleDataListener.stopAlarmListener()
  }

  fun redirectReportPage() {
    navController.navigate(MainDestinations.REPORT_ROUTE)
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 30.dp, vertical = 20.dp)
  ) {
    Text("수면시간 체크", fontWeight = FontWeight.Bold, fontSize = 24.sp)

    Spacer(modifier = Modifier.height(10.dp))

    CircularTimePicker(startTime, endTime, durationState)

    Spacer(modifier = Modifier.height(10.dp))

    TimeSelection(startTime.value, endTime.value)

    SwitchAction()

    ButtonAction(
      isStarted, isAlarm,
      startSleep = { startSleep() },
      cancelSleep = { cancelSleep() },
      stopAlarm = { stopAlarm() }
    )
    Text(log)
    Button(colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
      onClick = { redirectReportPage() }) {
      Text(
        text = "Report Page"
      )
    }
  }
}

@Preview
@Composable
fun PreviewSleepActivity() {
  SleepActivity()
}
package com.sewon.topperhealth.screen.activity

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.icu.util.GregorianCalendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.sewon.topperhealth.service.bluetooth.LowEnergyGatt
import com.sewon.topperhealth.MainActivity
import com.sewon.topperhealth.screen.a0common.Destinations
import com.sewon.topperhealth.screen.activity.component.CircularTimePicker
import com.sewon.topperhealth.screen.activity.component.ModalAssessment
import com.sewon.topperhealth.screen.activity.component.ModalQuality
import com.sewon.topperhealth.screen.activity.sub.ButtonAction
import com.sewon.topperhealth.screen.activity.sub.SwitchAction
import com.sewon.topperhealth.screen.activity.sub.TimeSelection
import com.sewon.topperhealth.service.alarm.AlarmReceiver
import timber.log.Timber


@SuppressLint("MissingPermission")
@Composable
fun SleepActivity(
  modifier: Modifier = Modifier,
  navController: NavHostController = rememberNavController(),
  viewModel: ActivityViewModel = hiltViewModel(),
) {
  val context = LocalContext.current
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  val log by remember {
    mutableStateOf(MainActivity.lowEnergyListener.log)
  }


  val deviceAddress by MainActivity.lowEnergyService.deviceAddress


  fun composeConnectToSensor() {
    try {
      val adapter = context.getSystemService<BluetoothManager>()?.adapter
      val device = adapter?.getRemoteDevice(deviceAddress)
      val socket = device?.let {
        LowEnergyGatt(context, it)
      }
      if (socket != null) {
        MainActivity.lowEnergyService.connect(socket)
      }
    } catch (exception: IllegalArgumentException) {
      Timber.tag("composeConnectToSensor").w(exception)
    }
  }

  val isStarted = remember { mutableStateOf(false) }
  val isAlarm = remember { mutableStateOf(MainActivity.lowEnergyListener.isAlarm) }


  fun startSleep() {
    MainActivity.lowEnergyService.playSoundSleepInduce()

    isStarted.value = true

    val startTimeCalendar = GregorianCalendar.getInstance()
    startTimeCalendar.set(GregorianCalendar.HOUR_OF_DAY, uiState.startTime.hour)
    startTimeCalendar.set(GregorianCalendar.MINUTE, uiState.startTime.minute)
    startTimeCalendar.set(GregorianCalendar.SECOND, 0)

    val endTimeCalendar = GregorianCalendar.getInstance()
    endTimeCalendar.set(GregorianCalendar.HOUR_OF_DAY, uiState.endTime.hour)
    endTimeCalendar.set(GregorianCalendar.MINUTE, uiState.endTime.minute)
    endTimeCalendar.set(GregorianCalendar.SECOND, 0)

    if (endTimeCalendar < GregorianCalendar.getInstance()) {
      endTimeCalendar.add(GregorianCalendar.DAY_OF_MONTH, 1)
    }

    viewModel.createSession(startTimeCalendar, endTimeCalendar)
    composeConnectToSensor()

    val alarmIntent = Intent(context, AlarmReceiver::class.java)
    MainActivity.alarmPendingIntent =
      PendingIntent.getBroadcast(context, 1234, alarmIntent, PendingIntent.FLAG_MUTABLE)

//    val calendar = GregorianCalendar.getInstance()
//    calendar[GregorianCalendar.SECOND] = calendar[GregorianCalendar.SECOND] + 5

//    MainActivity.alarmManager
//      .setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), DAY, MainActivity.alarmPendingIntent)
    MainActivity.alarmManager.set(
      AlarmManager.RTC_WAKEUP,
      endTimeCalendar.timeInMillis,
      MainActivity.alarmPendingIntent
    )
  }

  fun cancelSleep() {
    isStarted.value = false
    MainActivity.lowEnergyService.stopSoundSleepInduce()
    MainActivity.alarmManager.cancel(MainActivity.alarmPendingIntent)

    viewModel.updateCurrentSessionEndTime()
    MainActivity.lowEnergyService.disconnectBluetoothSocket()
  }

  fun stopAlarm() {
    MainActivity.lowEnergyListener.stopAlarmListener()
  }

  fun redirectReportPage() {
    navController.navigate(Destinations.REPORT_ROUTE)
  }


  var openAssessmentModal by rememberSaveable { mutableStateOf(false) }
  var openQualityModal by rememberSaveable { mutableStateOf(false) }



  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 30.dp, vertical = 20.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text("수면시간 체크", fontWeight = FontWeight.Bold, fontSize = 24.sp)

      Button(colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
        onClick = { redirectReportPage() }) {
        Text(
          text = "Report"
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    CircularTimePicker(uiState.startTime, uiState.endTime,
      updateStartTime = {
        viewModel.updateStartTime(it)
      }, updateEndTime = {
        viewModel.updateEndTime(it)
      })

    Spacer(modifier = Modifier.height(10.dp))

    Column(Modifier.verticalScroll(rememberScrollState())) {
      TimeSelection(uiState.startTime, uiState.endTime)

      SwitchAction()

      Spacer(modifier = Modifier.height(10.dp))

      ButtonAction(
        isStarted.value, isAlarm.value,
        startSleep = { startSleep() },
        cancelSleep = {
          cancelSleep()
          openAssessmentModal = !openAssessmentModal
        },
        stopAlarm = { stopAlarm() }
      )
      Text(log)
    }

    if (openAssessmentModal) {
      ModalAssessment(onToggleModal = {
        openAssessmentModal = !openAssessmentModal
      },
        onSaveAssessment = {
          viewModel.saveAssessment(it)
          openAssessmentModal = !openAssessmentModal
          openQualityModal = true
        }
      )
    }

    if (openQualityModal) {
      ModalQuality(onToggleModal = {
        openQualityModal = !openQualityModal
      },
        onSaveQuality = { rating, memo ->
          openQualityModal = !openQualityModal
          viewModel.saveQuality(rating, memo)
        }
      )
    }
  }
}

@Preview
@Composable
fun PreviewSleepActivity() {
  SleepActivity()
}
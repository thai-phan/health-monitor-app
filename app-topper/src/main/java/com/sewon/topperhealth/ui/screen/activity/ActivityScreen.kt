package com.sewon.topperhealth.ui.screen.activity

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sewon.topperhealth.MainActivity
import com.sewon.topperhealth.R
import com.sewon.topperhealth.data.DataStoreManager

import com.sewon.topperhealth.service.alarm.AlarmReceiver
import com.sewon.topperhealth.service.algorithm.sleep.AlgorithmConstants
import com.sewon.topperhealth.service.bluetooth.LowEnergyClient
import com.sewon.topperhealth.service.bluetooth.LowEnergyGatt
import com.sewon.topperhealth.service.bluetooth.util.Connected
import com.sewon.topperhealth.ui.screen.activity.component.DebugActivityLog
import com.sewon.topperhealth.ui.screen.activity.childc.ButtonAction
import com.sewon.topperhealth.ui.screen.activity.childb.SwitchAction
import com.sewon.topperhealth.ui.screen.activity.childa.TimeSelection
import com.sewon.topperhealth.ui.screen.activity.component.CircularTimePicker
import com.sewon.topperhealth.ui.screen.activity.component.ModalAssessment
import com.sewon.topperhealth.ui.screen.activity.component.ModalStarQuality
import com.sewon.topperhealth.ui.theme.BackgroundMiddle
import com.sewon.topperhealth.ui.theme.topperTypography
import timber.log.Timber


@SuppressLint("MissingPermission")
@Composable
fun SleepActivity(
  modifier: Modifier = Modifier,
  viewModel: ActivityViewModel = hiltViewModel(),
  redirectAdvisePage: () -> Unit,
  redirectReportPage: () -> Unit,
) {

  val context = LocalContext.current
  val activity = LocalView.current.context as? Activity

  val dataStore = DataStoreManager(context)
  val isLogShowed by remember { dataStore.isLogShowed }.collectAsState(initial = false)
  val isDimDisabled by remember { dataStore.isDimDisabled }.collectAsState(initial = false)
  val referenceCount by remember { dataStore.referenceCount }.collectAsState(initial = AlgorithmConstants.REF_COUNT)


  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  val deviceAddress by LowEnergyClient.deviceAddress.observeAsState()
  val isAlarm by LowEnergyClient.isAlarm.observeAsState()
  val isStarted by LowEnergyClient.isStarted.observeAsState()
  val lowEnergyConnectState = LowEnergyClient.connected.observeAsState()

  val openDevMode = rememberSaveable { mutableStateOf(false) }

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


  fun startSleep() {
    if (activity != null && !isDimDisabled) {
      activity.window?.attributes = activity.window.attributes.apply {
        screenBrightness = 0.2f
      }
    }
    val startTimeCalendar = viewModel.getCalendarFromTimePicker(uiState.startTime)
    val endTimeCalendar = viewModel.getCalendarFromTimePicker(uiState.endTime)
    viewModel.startSession(referenceCount, startTimeCalendar, endTimeCalendar)

    composeConnectToSensor()

    val alarmIntent = Intent(context, AlarmReceiver::class.java)
    MainActivity.alarmPendingIntent =
      PendingIntent.getBroadcast(context, 1234, alarmIntent, PendingIntent.FLAG_MUTABLE)

    MainActivity.alarmManager.set(
      AlarmManager.RTC_WAKEUP,
      endTimeCalendar.timeInMillis,
      MainActivity.alarmPendingIntent
    )
  }

  fun cancelSleep() {
    if (activity != null) {
      activity.window?.attributes = activity.window.attributes.apply {
        screenBrightness = -1.0f
      }
    }

    viewModel.endSession()
  }

  fun stopAlarm() {
    MainActivity.lowEnergyClient.stopAlarmListener()
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
      Text(stringResource(R.string.sleep_time_check), style = topperTypography.headlineSmall)

      Button(
        modifier = Modifier
          .height(1.dp)
          .width(1.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = Color.Transparent,
          contentColor = Color.Transparent
        ),
        onClick = { openDevMode.value = true }) {
      }

      Button(colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
        onClick = { redirectReportPage() }) {
        Text(stringResource(R.string.report), color = BackgroundMiddle)
      }
    }

    Spacer(Modifier.height(10.dp))

    CircularTimePicker(uiState.startTime, uiState.endTime,
      updateStartTime = {
        viewModel.updateStartTime(it)
      }, updateEndTime = {
        viewModel.updateEndTime(it)
      })

    Spacer(Modifier.height(10.dp))

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState()),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      TimeSelection(uiState.startTime, uiState.endTime)
      Spacer(Modifier.height(10.dp))
      SwitchAction()
      Spacer(Modifier.height(10.dp))
      TextButton(
        onClick = {

        }) {
        Text("Sensor ")
        when (lowEnergyConnectState.value) {
          Connected.NotConnected -> Text("Not Connected")
          Connected.Pending -> Text("Pending")
          Connected.True -> Text("Connected")
          Connected.False -> Text("Disconnected")
          else -> Text("Not Connected")
        }
      }
      ButtonAction(
        isStarted!!, isAlarm!!,
        startSleep = { startSleep() },
        cancelSleep = {
          cancelSleep()
          openAssessmentModal = !openAssessmentModal
        },
        stopAlarm = { stopAlarm() }
      )

      if (isLogShowed) {
        DebugActivityLog()
      }
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
      ModalStarQuality(onToggleModal = {
        openQualityModal = !openQualityModal
      }) { rating, memo ->
        openQualityModal = !openQualityModal
        viewModel.savePSQI(rating, memo)
        redirectAdvisePage()
      }
    }


    if (openDevMode.value) {
      DialogDevMode() {
        openDevMode.value = !openDevMode.value
      }
    }
  }
}

//@Preview
//@Composable
//fun PreviewSleepActivity() {
//  SleepActivity()
//}
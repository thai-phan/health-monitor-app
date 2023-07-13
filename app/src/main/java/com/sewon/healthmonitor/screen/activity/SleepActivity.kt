package com.sewon.healthmonitor.screen.activity

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.sewon.healthmonitor.common.theme.checkedBorderColor
import com.sewon.healthmonitor.common.theme.checkedThumbColor
import com.sewon.healthmonitor.common.theme.checkedTrackColor
import com.sewon.healthmonitor.common.theme.uncheckedBorderColor
import com.sewon.healthmonitor.common.theme.uncheckedThumbColor
import com.sewon.healthmonitor.common.theme.uncheckedTrackColor
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepActivity(
    modifier: Modifier = Modifier,

    viewModel: SleepActivityViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val switchColors: SwitchColors = SwitchDefaults.colors(
        checkedThumbColor = checkedThumbColor,
        checkedTrackColor = checkedTrackColor,
        checkedBorderColor = checkedBorderColor,
        uncheckedThumbColor = uncheckedThumbColor,
        uncheckedTrackColor = uncheckedTrackColor,
        uncheckedBorderColor = uncheckedBorderColor,
    )
    var showTimePicker by remember { mutableStateOf(false) }
    val state = rememberTimePickerState()
    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .padding(horizontal = 30.dp, vertical = 20.dp)
    ) {
        Text("수면시간 체크", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Button(onClick = { viewModel.createNewTask() }) {
            Text("Click meee to add data")
        }
//        Button(onClick = { viewModel.createNewTask() }) {
//            Text("Click meee to add data")
//        }

        Spacer(modifier = Modifier.height(10.dp))
        CircularTimePicker()
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("취침시간")
                Text("12:00", fontWeight = FontWeight.Bold, fontSize = 30.sp)
                Text("PM")
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("기상시간")
                Text("12:00", fontWeight = FontWeight.Bold, fontSize = 30.sp)
                Text("AM")
            }
        }

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text("기상")
            }
        }

    }


    var timePickerColors: TimePickerColors = TimePickerDefaults.colors(
//        clockDialColor = clockDialColor,
//        clockDialSelectedContentColor = clockDialSelectedContentColor,
//        clockDialUnselectedContentColor = clockDialUnselectedContentColor,
//        selectorColor = selectorColor,
//        containerColor = containerColor,
//        periodSelectorBorderColor = periodSelectorBorderColor,
//        periodSelectorSelectedContainerColor = periodSelectorSelectedContainerColor,
//        periodSelectorUnselectedContainerColor = periodSelectorUnselectedContainerColor,
//        periodSelectorSelectedContentColor = periodSelectorSelectedContentColor,
//        periodSelectorUnselectedContentColor = periodSelectorUnselectedContentColor,
//        timeSelectorSelectedContainerColor = timeSelectorSelectedContainerColor,
//        timeSelectorUnselectedContainerColor = timeSelectorUnselectedContainerColor,
//        timeSelectorSelectedContentColor = timeSelectorSelectedContentColor,
//        timeSelectorUnselectedContentColor = timeSelectorUnselectedContentColor

    )


    if (showTimePicker) {
        TimePickerDialog(
            onCancel = { showTimePicker = false },
            onConfirm = {
                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, state.hour)
                cal.set(Calendar.MINUTE, state.minute)
                cal.isLenient = false
                snackScope.launch {
                    snackState.showSnackbar("Entered time: ${formatter.format(cal.time)}")
                }
                showTimePicker = false
            },
        ) {
            TimePicker(
                colors = timePickerColors, state = state
            )
        }

    }
}

@Preview
@Composable
fun PreviewSleepActivity() {
    SleepActivity()
}
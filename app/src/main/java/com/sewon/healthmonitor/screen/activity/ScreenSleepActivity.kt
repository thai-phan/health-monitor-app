package com.sewon.healthmonitor.screen.activity

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
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
import com.sewon.healthmonitor.screen.activity.component.CircularTimePicker
import com.sewon.healthmonitor.common.timepicker.TimeRangePicker

@SuppressLint("RememberReturnType")
@Composable
fun SleepActivity(
    modifier: Modifier = Modifier,

    viewModel: ViewModelSleepActivity = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val start = TimeRangePicker.Time(0, 0)
    val end = TimeRangePicker.Time(5, 30)

    val startTime = remember { mutableStateOf(start) }
    val endTime = remember { mutableStateOf(end) }

    val switchColors: SwitchColors = SwitchDefaults.colors(
        checkedThumbColor = checkedThumbColor,
        checkedTrackColor = checkedTrackColor,
        checkedBorderColor = checkedBorderColor,
        uncheckedThumbColor = uncheckedThumbColor,
        uncheckedTrackColor = uncheckedTrackColor,
        uncheckedBorderColor = uncheckedBorderColor,
    )

    Column(
        modifier = modifier
            .padding(horizontal = 30.dp, vertical = 20.dp)
    ) {
        Text("수면시간 체크", fontWeight = FontWeight.Bold, fontSize = 24.sp)

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {

                viewModel.queryFromServer()
            }) {
                Text("기상")
            }
            Button(onClick = {

                viewModel.queryFromServerHttp()
            }) {
                Text("http")
            }

        }

    }
}

@Preview
@Composable
fun PreviewSleepActivity() {
    SleepActivity()
}
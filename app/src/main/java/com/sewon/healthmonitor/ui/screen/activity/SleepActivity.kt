package com.sewon.healthmonitor.ui.screen.activity

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepActivity() {

    var showTimePicker by remember { mutableStateOf(false) }
    val state = rememberTimePickerState()
    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Row() {

            Column(modifier = Modifier.weight(1f)) {
                Text("123123")
                Text("2112312312")
                Text("asdfasdf")
                Text("sadfadsf")
                Row() {
                    Text("123123")
                    Switch(checked = true, onCheckedChange = {})
                }
                Row() {
                    Text("34234523")
                    Switch(checked = true, onCheckedChange = {})
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Box(propagateMinConstraints = false) {
                    Button(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = { showTimePicker = true }
                    ) { Text("Set Time") }
                    SnackbarHost(hostState = snackState)
                }
            }
        }

        Button(onClick = { /*TODO*/ }) {
            Text("asdfadfa")
        }
    }





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
            TimePicker(state = state)
        }

    }
}
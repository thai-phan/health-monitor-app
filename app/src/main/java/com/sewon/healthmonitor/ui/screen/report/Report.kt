package com.sewon.healthmonitor.ui.screen.report


import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Report() {
    val yellowBrush = Brush.verticalGradient(
        listOf(Color(0xFFFAFF00), Color(0xFFFF8A00))
    )
    val blueBrush = Brush.verticalGradient(
        listOf(Color(0xFF03DAC5), Color(0xFF3E4F9D))
    )

//    Donut chart 4 cai

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Text("수면 리포트")



        Row() {
            CircularChart(angle = 40f, modifier = Modifier.weight(1f), brush = yellowBrush)
            CircularChart(angle = 40f, modifier = Modifier.weight(1f), brush = blueBrush)
            CircularChart(angle = 80f, modifier = Modifier.weight(1f), brush = yellowBrush)
            CircularChart(angle = 70f, modifier = Modifier.weight(1f), brush = blueBrush)
        }

        Text("PQSI 수면평가점수")


    }


//    progress bar


//    line chart


}


@Preview
@Composable
fun PreviewReport() {
    Report()
}
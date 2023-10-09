package com.sewon.healthmonitor.screen.activity.component

import android.view.LayoutInflater
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.sewon.healthmonitor.R
import com.sewon.healthmonitor.common.timepicker.TimeRangePicker
import timber.log.Timber


@Composable
fun CircularTimePicker(
  startTime: TimeRangePicker.Time,
  endTime: TimeRangePicker.Time,
  updateStartTime: (TimeRangePicker.Time) -> Unit,
  updateEndTime: (TimeRangePicker.Time) -> Unit
) {
  Column {
    AndroidView(
      modifier = Modifier
        .fillMaxWidth(),
      factory = { context ->
        val view = LayoutInflater.from(context).inflate(R.layout.circular_time_picker, null)
        val timePicker = view.findViewById<TimeRangePicker>(R.id.circularTimePicker)
        timePicker.startTime = startTime
        timePicker.endTime = endTime

        timePicker.setOnTimeChangeListener(object : TimeRangePicker.OnTimeChangeListener {

          override fun onStartTimeChange(startTime: TimeRangePicker.Time) {
            updateStartTime(startTime)
          }

          override fun onEndTimeChange(endTime: TimeRangePicker.Time) {
            updateEndTime(endTime)
          }

          override fun onDurationChange(duration: TimeRangePicker.TimeDuration) {
//          durationState.value = duration
          }
        })
        timePicker
      },
      update = {
        it.startTime = startTime
        it.endTime = endTime
      }
    )
  }

}
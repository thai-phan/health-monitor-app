package com.sewon.healthmonitor.screen.activity.component

import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.sewon.healthmonitor.R
import com.sewon.healthmonitor.common.timepicker.TimeRangePicker


@Composable
fun CircularTimePicker(
  startTimeState: MutableState<TimeRangePicker.Time>,
  endTimeState: MutableState<TimeRangePicker.Time>,
  durationState: MutableState<TimeRangePicker.TimeDuration>
) {
  AndroidView(
    modifier = Modifier
      .fillMaxWidth(),
    factory = { context ->
      val view = LayoutInflater.from(context).inflate(R.layout.circular_time_picker, null)
      val timePicker = view.findViewById<TimeRangePicker>(R.id.circularTimePicker)
      timePicker.startTime = startTimeState.value
      timePicker.endTime = endTimeState.value

      timePicker.setOnTimeChangeListener(object : TimeRangePicker.OnTimeChangeListener {
        override fun onStartTimeChange(startTime: TimeRangePicker.Time) {
          startTimeState.value = startTime
        }

        override fun onEndTimeChange(endTime: TimeRangePicker.Time) {
          endTimeState.value = endTime
        }

        override fun onDurationChange(duration: TimeRangePicker.TimeDuration) {
          durationState.value = duration
        }
      })
      timePicker
    }
  )
}
package com.sewon.healthmonitor.screen.activity.component

import android.content.res.Resources
import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.sewon.healthmonitor.R
import nl.joery.timerangepicker.TimeRangePicker
import timber.log.Timber


val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

@Composable
fun CircularTimePicker(
    startTimeState: MutableState<TimeRangePicker.Time>,
    endTimeState: MutableState<TimeRangePicker.Time>
) {


    AndroidView(
        modifier = Modifier
            .fillMaxWidth(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.circular_time_picker, null)
            val timePicker = view.findViewById<TimeRangePicker>(R.id.circularTimePicker)
            timePicker.startTime = TimeRangePicker.Time(0, 10)
            timePicker.endTime = TimeRangePicker.Time(5, 30)

            timePicker.setOnTimeChangeListener(object : TimeRangePicker.OnTimeChangeListener {
                override fun onStartTimeChange(startTime: TimeRangePicker.Time) {
                    startTimeState.value = startTime
                    Timber.d("Start time: %s", startTime)
                }

                override fun onEndTimeChange(endTime: TimeRangePicker.Time) {
                    endTimeState.value = endTime
                    Timber.d("End time: %s", endTime.hour)
                }

                override fun onDurationChange(duration: TimeRangePicker.TimeDuration) {
                    Timber.d("Duration: %s", duration.hour)
                }
            })
            timePicker
        }
    )
//    custom_time_picker.xml
}
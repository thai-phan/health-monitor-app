package com.sewon.healthmonitor.screen.activity.component

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.sewon.healthmonitor.R
import nl.joery.timerangepicker.TimeRangePicker
import timber.log.Timber


val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

@SuppressLint("MissingInflatedId")
@Composable
fun CircularTimePicker() {

    AndroidView(
        modifier = Modifier
            .fillMaxWidth(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.circular_time_picker, null)
            val timePicker = view.findViewById<TimeRangePicker>(R.id.circularTimePicker)
            timePicker.setOnTimeChangeListener(object : TimeRangePicker.OnTimeChangeListener {
                override fun onStartTimeChange(startTime: TimeRangePicker.Time) {
                    Timber.d("Start time: %s", startTime)
                }

                override fun onEndTimeChange(endTime: TimeRangePicker.Time) {
                    Timber.d("End time: %s", endTime.hour)
                }

                override fun onDurationChange(duration: TimeRangePicker.TimeDuration) {
                    Timber.d("Duration: %s", duration.hour)
                }
            })

//            timePicker.sliderColor = Color.parseColor("#88141218")
//            timePicker.sliderWidth = 20.px
//            timePicker.sliderRangeColor = Color.parseColor("#03DAC5")

//            timePicker.sliderColor
            timePicker
        }
    )
//    custom_time_picker.xml
}
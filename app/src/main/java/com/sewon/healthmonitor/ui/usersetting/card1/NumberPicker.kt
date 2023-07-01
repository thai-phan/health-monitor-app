package com.sewon.healthmonitor.ui.usersetting.card1

import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.sewon.healthmonitor.R

@Composable
fun NumberPicker() {

    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->

            val view = LayoutInflater.from(context).inflate(R.layout.number_picker, null)
            val picker = view.findViewById<NumberPicker>(R.id.number_picker)

            val data = arrayOf("Berlin", "Moscow", "Tokyo", "Paris")
            picker.minValue = 0
            picker.maxValue = data.size - 1
            picker.displayedValues = data

            picker

        }

    )

}
package com.sewon.healthmonitor.ui.screen.setting.card1

import android.view.LayoutInflater
import android.widget.DatePicker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.sewon.healthmonitor.R
import java.util.Calendar

@Composable
fun DatePicker(
//    onDateSelected: (Date) -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.date_picker, null)
            val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
            val calendar = Calendar.getInstance() // show today by default
            datePicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ) { _, year, monthOfYear, dayOfMonth ->
                val date = Calendar.getInstance().apply {
                    set(year, monthOfYear, dayOfMonth)
                }.time
//                onSelectedDateUpdate(date)
            }
            datePicker
        }
    )
}
package com.sewon.healthmonitor.ui.usersetting.card1

import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.sewon.healthmonitor.R


@Composable
fun Spinner() {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->

            val courses = arrayOf(
                "C", "Data structures",
                "Interview prep", "Algorithms",
                "DSA with java", "OS"
            )
            val view = LayoutInflater.from(context).inflate(R.layout.spinner, null)
            val spinner = view.findViewById<Spinner>(R.id.spinner)

            val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
                context,
                android.R.layout.simple_spinner_item,
                courses
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner.adapter = adapter
            }
            ad.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
            )

            spinner.adapter = ad

            spinner



        }
    )
}
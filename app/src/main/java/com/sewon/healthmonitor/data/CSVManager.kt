package com.sewon.healthmonitor.data

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset

import com.sewon.healthmonitor.R
import com.sewon.healthmonitor.data.source.local.entity.LocalRadar


class CSVManager(private val context: Context) {

    suspend fun findResource() {
        val inputStreammm = context.resources.openRawResource(R.raw.sewon_data_1)
        print("asdf")
        val reader = BufferedReader(
            InputStreamReader(inputStreammm, Charset.forName("UTF-8"))
        )
        var line = ""
//        var sampleDb = AppDatabase.getInstance(context);
//        var dao = sampleDb?.topperDao();

        try {
            while (reader.readLine().also { line = it } != null) {
                // Split the line into different tokens (using the comma as a separator).
                val tokens = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()

                // Read the data and store it in the WellData POJO.


                var localRadar: LocalRadar = LocalRadar(
                    rb = tokens[0].toFloat(),
                    hr = tokens[0].toFloat(),
                    rri = tokens[0].toFloat(),
                    moving = tokens[0],
                    detect = tokens[0],
                    noOne = tokens[0],
                    stable = tokens[0]
                )

            }
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
    }
}



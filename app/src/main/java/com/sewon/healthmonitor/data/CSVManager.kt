package com.sewon.healthmonitor.data

import android.content.Context
import android.util.Half.toFloat
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset

import com.sewon.healthmonitor.R
import com.sewon.healthmonitor.data.entity.Topper


class RegularClass(private val context: Context) {

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


                var topper: Topper = Topper(
                    rb = tokens[0].toFloat(),
                    hr = tokens[0].toFloat(),
                    rri = tokens[0].toFloat(),
                    moving = tokens[0],
                    detect = tokens[0],
                    noOne = tokens[0],
                    stable = tokens[0]
                )
//
//                if (dao != null) {
//                    dao.insertAll(topper)
//                }


//                val wellData = WellData()
//                wellData.setOwner(tokens[0])
//                wellData.setApi(tokens[1])
//                wellData.setLongitude(tokens[2])
//                wellData.setLatitude(tokens[3])
//                wellData.setProperty(tokens[4])
//                wellData.setWellName(tokens[5])
//                wellDataList.add(wellData)
                Log.d("MainActivity", "Just Created wellData")
            }
        } catch (e1: IOException) {
            Log.e("MainActivity", "Error$line", e1)
            e1.printStackTrace()
        }
    }
}



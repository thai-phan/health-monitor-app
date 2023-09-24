package com.sewon.healthmonitor.service

import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.sewon.healthmonitor.R


class LightActivity : Activity() {
  var textLIGHT_available: TextView? = null
  var textLIGHT_reading: TextView? = null
  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.light_main)
    textLIGHT_available = findViewById<View>(R.id.LIGHT_available) as TextView
    textLIGHT_reading = findViewById<View>(R.id.LIGHT_reading) as TextView
    val mySensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
    val lightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    if (lightSensor != null) {
      textLIGHT_available!!.text = "Sensor.TYPE_LIGHT Available"
      mySensorManager.registerListener(
        lightSensorListener,
        lightSensor,
        SensorManager.SENSOR_DELAY_NORMAL
      )
    } else {
      textLIGHT_available!!.text = "Sensor.TYPE_LIGHT NOT Available"
    }
  }

  private val lightSensorListener: SensorEventListener = object : SensorEventListener {
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
      // TODO Auto-generated method stub
    }

    override fun onSensorChanged(event: SensorEvent) {
      if (event.sensor.type == Sensor.TYPE_LIGHT) {
        textLIGHT_reading!!.text = "LIGHT: " + event.values[0]
      }
    }
  }
}
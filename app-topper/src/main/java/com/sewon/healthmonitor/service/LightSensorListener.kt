package com.sewon.healthmonitor.service

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

class LightSensorListener : SensorEventListener {
  override fun onSensorChanged(event: SensorEvent) {
    val lux = event.values[0]

  }

  override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    print("adsfadf")
  }
}
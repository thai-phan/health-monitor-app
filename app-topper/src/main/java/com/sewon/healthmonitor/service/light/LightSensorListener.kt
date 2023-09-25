package com.sewon.healthmonitor.service.light

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import timber.log.Timber

class LightSensorListener : SensorEventListener {
  override fun onSensorChanged(event: SensorEvent) {
    val lux = event.values[0]
    Timber.d(lux.toString())
  }

  override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    print("adsfadf")
  }
}
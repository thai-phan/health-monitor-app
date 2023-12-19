package com.sewon.topperhealth

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.sewon.topperhealth.screen.RootCompose
import com.sewon.topperhealth.service.bluetooth.ClassicClient
import com.sewon.topperhealth.service.bluetooth.ClassicService
import com.sewon.topperhealth.service.bluetooth.LowEnergyClient
import com.sewon.topperhealth.service.bluetooth.LowEnergyService
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  companion object {
    lateinit var lowEnergyService: LowEnergyService
    lateinit var classicService: ClassicService

    lateinit var alarmManager: AlarmManager
    lateinit var alarmPendingIntent: PendingIntent

    var lowEnergyClient = LowEnergyClient()
    var classicClient = ClassicClient()
  }

  private lateinit var sensorManager: SensorManager
  private var lightSensor: Sensor? = null
  private var proximitySensor: Sensor? = null
//  val dataStore = HealthDataStore(applicationContext)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    actionBar?.hide()
    WindowCompat.setDecorFitsSystemWindows(window, false)

    sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
    lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

    setContent {
      RootCompose(setLocale = {
        reload()
      }) {
        finish()
      }
    }
  }

  private fun reload() {
    recreate()
  }

  override fun onResume() {
    super.onResume()

    if (lightSensor != null) {
      sensorManager.registerListener(
        lightSensorListener,
        lightSensor,
        SensorManager.SENSOR_DELAY_NORMAL
      )
    }
    if (proximitySensor != null) {
      sensorManager.registerListener(
        proximitySensorListener,
        proximitySensor,
        SensorManager.SENSOR_DELAY_NORMAL
      )
    }

    setLocale("ko")
  }

//  override fun attachBaseContext(base: Context) {
//    super.attachBaseContext(updateResources(base))
//  }

  private fun updateResources(context: Context): Context {
    val config = Configuration(context.resources.configuration)
    config.setLocale(Locale("en"))
    return context.createConfigurationContext(config)
  }

  private fun setLocale(localeStr: String) {
    Locale.setDefault(Locale(localeStr))
    val config = resources.configuration
    config.setLocale(Locale(localeStr))
    resources.updateConfiguration(config, resources.displayMetrics)
  }


  override fun onStart() {
    super.onStart()
    alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

    val bleIntent = Intent(this, LowEnergyService::class.java)
    startService(bleIntent)
    bindService(bleIntent, lowEnergyServiceConnection, BIND_AUTO_CREATE)

    val blcIntent = Intent(this, ClassicService::class.java)
    startService(blcIntent)
    bindService(blcIntent, classicServiceConnection, BIND_AUTO_CREATE)
  }

  override fun onPause() {
//    mSensorManager.unregisterListener(sensorListener)
    super.onPause()
  }

  override fun onStop() {
    super.onStop()
    unbindService(lowEnergyServiceConnection)
    unbindService(classicServiceConnection)
  }

  override fun onRestart() {
    super.onRestart()
  }

  override fun onDestroy() {
    super.onDestroy()
  }


  private val lowEnergyServiceConnection: ServiceConnection = object : ServiceConnection {
    override fun onServiceDisconnected(name: ComponentName) {}

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
      lowEnergyService = (service as LowEnergyService.ServiceBinder).service
      lowEnergyService.attach(lowEnergyClient)
    }
  }

  private val classicServiceConnection: ServiceConnection = object : ServiceConnection {
    override fun onServiceDisconnected(name: ComponentName) {}

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
      classicService = (service as ClassicService.ServiceBinder).service
      classicService.attach(classicClient)
    }
  }

  private val lightSensorListener: SensorEventListener = object : SensorEventListener {
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
      // TODO Auto-generated method stub
    }

    override fun onSensorChanged(event: SensorEvent) {
      if (event.sensor.type == Sensor.TYPE_LIGHT) {
        if (event.values[0].toInt() < 100) {
          window.attributes = window.attributes.apply {
            screenBrightness = 0.0f
          }
        } else {
          window.attributes = window.attributes.apply {
            screenBrightness = -1.0f
          }
        }
      }
    }
  }

  private val proximitySensorListener: SensorEventListener = object : SensorEventListener {
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
      // TODO Auto-generated method stub
    }

    override fun onSensorChanged(event: SensorEvent) {
      if (event.sensor.type == Sensor.TYPE_PROXIMITY) {
        if (event.values[0].toInt() < 1) {
          window.attributes = window.attributes.apply {
            screenBrightness = 0.0f
          }
        } else {
          window.attributes = window.attributes.apply {
            screenBrightness = -1.0f
          }
        }
      }
    }
  }
}

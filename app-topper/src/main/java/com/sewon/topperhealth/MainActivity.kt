package com.sewon.topperhealth

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.sewon.topperhealth.data.DataStoreManager
import com.sewon.topperhealth.screen.RootCompose
import com.sewon.topperhealth.service.bluetooth.ClassicClient
import com.sewon.topperhealth.service.bluetooth.ClassicService
import com.sewon.topperhealth.service.bluetooth.LowEnergyClient
import com.sewon.topperhealth.service.bluetooth.LowEnergyService
import com.sewon.topperhealth.util.LanguageHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


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

  private lateinit var dataStore: DataStoreManager

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

    observeLanguageChanges()
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
  }

  private var selectedLang = ""
  private var selectedLangCode = ""

  override fun attachBaseContext(newBase: Context?) {
    var base = newBase
    newBase?.let { context ->
      dataStore = DataStoreManager(context)
      val appLanguage = dataStore.currentLanguage
      selectedLang = appLanguage.selectedLang
      selectedLangCode = appLanguage.selectedLangCode
      base = LanguageHelper.changeLanguage(context, selectedLangCode)
    }

    super.attachBaseContext(base)
  }

  private fun restartActivity() {
    startActivity(Intent(this, MainActivity::class.java))
    finish()
  }

  private fun observeLanguageChanges() {
    lifecycleScope.launch {
      dataStore.observeLanguageChange().collect {
        if (selectedLangCode != it.selectedLangCode) {
          restartActivity()
        }
      }
    }
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
    super.onPause()
  }

  override fun onStop() {
    super.onStop()
    unbindService(lowEnergyServiceConnection)
    unbindService(classicServiceConnection)
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

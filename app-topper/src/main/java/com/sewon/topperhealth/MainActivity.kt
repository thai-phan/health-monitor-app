package com.sewon.topperhealth

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Configuration
import android.os.Build
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    actionBar?.hide()
    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      RootCompose {
        finish()
      }
    }
  }

  override fun onResume() {
    super.onResume()
//    val locale = Locale("ko")
//    Locale.setDefault(locale)
//    val config: Configuration = baseContext.resources.configuration
//    config.setLocale(locale)
//    val resources: Resources = context.getResources()
//    createConfigurationContext(config);
  }

  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(updateResources(base))
  }

  private fun updateResources(context: Context): Context {
    val resources = context.resources
    val config = Configuration(resources.configuration)
    val locale = Locale("en")
    config.setLocale(locale)
    return context.createConfigurationContext(config)
  }


  private fun setLocale(locale: Locale) {
    val resources = resources
    val configuration = resources.configuration
    configuration.setLocale(locale)
    applicationContext.createConfigurationContext(configuration)
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


  /**
   * Interface for monitoring the state of an application service. See Service and Context.bindService() for more information.
   * Like many callbacks from the system, the methods on this class are called from the main thread of your process.
   **/
  private val lowEnergyServiceConnection: ServiceConnection = object : ServiceConnection {
    override fun onServiceDisconnected(name: ComponentName) {
//      serviceBleHandler = null
    }

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
      lowEnergyService = (service as LowEnergyService.ServiceBinder).service
      lowEnergyService.attach(lowEnergyClient)
    }
  }

  private val classicServiceConnection: ServiceConnection = object : ServiceConnection {
    override fun onServiceDisconnected(name: ComponentName) {
//      serviceBleHandler = null
    }

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
      classicService = (service as ClassicService.ServiceBinder).service
      classicService.attach(classicClient)
    }
  }

}

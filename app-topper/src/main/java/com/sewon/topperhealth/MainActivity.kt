package com.sewon.topperhealth

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import com.sewon.topperhealth.screen.a0common.RootCompose
import com.sewon.topperhealth.service.algorithm.sleep.report.ReportDataProcessing
import com.sewon.topperhealth.service.blc.BlcService
import com.sewon.topperhealth.service.blc.BlcListener
import com.sewon.topperhealth.service.ble.BleListener
import com.sewon.topperhealth.service.ble.BleService
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  companion object {
    lateinit var bleService: BleService
    lateinit var blcService: BlcService

    lateinit var alarmManager: AlarmManager
    lateinit var alarmPendingIntent: PendingIntent
    lateinit var reportDataProcessing: ReportDataProcessing

    var bleListener = BleListener()
    var blcListener = BlcListener()

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

  @RequiresApi(Build.VERSION_CODES.S)
  override fun onResume() {
    super.onResume()

  }


  override fun onStart() {
    super.onStart()

    alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

    val bleIntent = Intent(this, BleService::class.java)
    startService(bleIntent)
    bindService(bleIntent, bleServiceConnection, BIND_AUTO_CREATE)

    val blcIntent = Intent(this, BlcService::class.java)
    startService(blcIntent)
    bindService(blcIntent, blcServiceConnection, BIND_AUTO_CREATE)
  }

  override fun onPause() {
//    mSensorManager.unregisterListener(sensorListener)
    super.onPause()
  }

  override fun onStop() {
    super.onStop()
    unbindService(bleServiceConnection)
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
  private val bleServiceConnection: ServiceConnection = object : ServiceConnection {
    override fun onServiceDisconnected(name: ComponentName) {
//      serviceBleHandler = null
    }

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
      bleService = (service as BleService.SerialBinder).service
      bleService.attach(bleListener)
    }
  }

  private val blcServiceConnection: ServiceConnection = object : ServiceConnection {
    override fun onServiceDisconnected(name: ComponentName) {
//      serviceBleHandler = null
    }

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
      blcService = (service as BlcService.SerialBinder).service
      blcService.attach(blcListener)
    }
  }

}

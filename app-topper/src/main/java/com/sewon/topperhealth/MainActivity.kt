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
import com.sewon.topperhealth.service.ble.BleServiceListener
import com.sewon.topperhealth.service.ble.BleServiceHandler
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  companion object {
    lateinit var bleServiceHandler: BleServiceHandler
    lateinit var alarmManager: AlarmManager
    lateinit var alarmPendingIntent: PendingIntent
    lateinit var reportDataProcessing: ReportDataProcessing

    var listenerBleStream = BleServiceListener()

  }

  override fun onCreate(savedInstanceState: Bundle?) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//    StrictMode.setThreadPolicy(
//      ThreadPolicy.Builder()
//        .detectDiskReads()
//        .detectDiskWrites()
//        .detectNetwork() // or .detectAll() for all detectable problems
//        .penaltyLog()
//        .build()
//    )
//    StrictMode.setVmPolicy(
//      VmPolicy.Builder()
//        .detectLeakedSqlLiteObjects()
//        .detectLeakedClosableObjects()
//        .penaltyLog()
//        .penaltyDeath()
//        .build()
//    )
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

    val intent = Intent(this, BleServiceHandler::class.java)
    startService(intent)
    bindService(intent, bleServiceConnection, BIND_AUTO_CREATE)
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
      bleServiceHandler = (service as BleServiceHandler.SerialBinder).service
      bleServiceHandler.attach(listenerBleStream)
    }
  }

  private val blcServiceConnection: ServiceConnection = object : ServiceConnection {
    override fun onServiceDisconnected(name: ComponentName) {
//      serviceBleHandler = null
    }

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
      bleServiceHandler = (service as BleServiceHandler.SerialBinder).service
      bleServiceHandler.attach(listenerBleStream)
    }
  }

}

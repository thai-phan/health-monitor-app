package com.sewon.healthmonitor

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import com.sewon.healthmonitor.common.RootCompose
import com.sewon.healthmonitor.service.algorithm.DataService
import com.sewon.healthmonitor.service.ble.BleDataListener
import com.sewon.healthmonitor.service.ble.BleHandleService
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  companion object {
    lateinit var bleHandleService: BleHandleService
    lateinit var alarmManager: AlarmManager
    lateinit var alarmPendingIntent: PendingIntent
    lateinit var dataService: DataService
//    lateinit var mSensorManager: SensorManager
//    lateinit var mLightSensor: Sensor
//    lateinit var sensorListener: LightSensorListener

    var bleDataListener = BleDataListener()

  }

  override fun onCreate(savedInstanceState: Bundle?) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
    StrictMode.setThreadPolicy(
      ThreadPolicy.Builder()
        .detectDiskReads()
        .detectDiskWrites()
        .detectNetwork() // or .detectAll() for all detectable problems
        .penaltyLog()
        .build()
    )
    StrictMode.setVmPolicy(
      VmPolicy.Builder()
        .detectLeakedSqlLiteObjects()
        .detectLeakedClosableObjects()
        .penaltyLog()
        .penaltyDeath()
        .build()
    )
    super.onCreate(savedInstanceState)

    actionBar?.hide()

//    mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager;
//    mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)!!;

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

//    mSensorManager.registerListener(sensorListener, mLightSensor, SENSOR_DELAY_NORMAL)


  }


  override fun onStart() {
    super.onStart()

    alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

    val intent = Intent(this, BleHandleService::class.java)
    startService(intent)
    bindService(intent, mServiceConnection, BIND_AUTO_CREATE)
  }

  override fun onStop() {
    super.onStop()
//    if (mServiceBound) {
    unbindService(mServiceConnection)
  }

  private val mServiceConnection: ServiceConnection = object : ServiceConnection {
    override fun onServiceDisconnected(name: ComponentName) {
//      TODO mServiceBound = false
    }

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
      Timber.tag("Timber").d("onServiceConnected")
      bleHandleService = (service as BleHandleService.SerialBinder).service
      bleHandleService.attach(bleDataListener)
    }
  }

  override fun onPause() {
//    mSensorManager.unregisterListener(sensorListener)
    super.onPause()
  }

  override fun onRestart() {
    super.onRestart()
  }

  override fun onDestroy() {
    super.onDestroy()
  }

}

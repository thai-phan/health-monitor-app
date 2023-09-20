package com.sewon.healthmonitor

import android.app.AlarmManager
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.sewon.healthmonitor.common.RootCompose
import com.sewon.healthmonitor.service.ble.BleDataListener
import com.sewon.healthmonitor.service.ble.BleHandleService
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  companion object {
    lateinit var bleHandleService: BleHandleService
    lateinit var alarmManager: AlarmManager

    var bleDataListener = BleDataListener()

  }

  override fun onCreate(savedInstanceState: Bundle?) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
    actionBar?.hide();
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      RootCompose {
        finish()
      }
    }
  }

  override fun onResume() {
    super.onResume()
  }

  override fun onStart() {
    super.onStart()

    alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager;

    val intent = Intent(this, BleHandleService::class.java)
    startService(intent)
    bindService(intent, mServiceConnection, BIND_AUTO_CREATE)
  }

  override fun onPause() {
    super.onPause()
  }

  override fun onStop() {
    super.onStop()
//    if (mServiceBound) {
    unbindService(mServiceConnection)
  }

  override fun onRestart() {
    super.onRestart()
  }

  override fun onDestroy() {
    super.onDestroy()
  }

  private val mServiceConnection: ServiceConnection = object : ServiceConnection {
    override fun onServiceDisconnected(name: ComponentName) {
//      TODO mServiceBound = false
    }

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
      Timber.tag("Timber").d("onServiceConnected")
      bleHandleService = (service as BleHandleService.SerialBinder).service
      bleHandleService.attach(bleDataListener)
//      val myBinder = service
//      TODO mServiceBound = true
    }
  }
}

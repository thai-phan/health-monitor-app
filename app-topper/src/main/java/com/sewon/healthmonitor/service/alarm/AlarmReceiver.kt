package com.sewon.healthmonitor.service.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.VIBRATOR_MANAGER_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.sewon.healthmonitor.MainActivity


class AlarmReceiver : BroadcastReceiver() {
  companion object {
    lateinit var ringtone: Ringtone
  }

  override fun onReceive(context: Context, intent: Intent) {
    MainActivity.bleDataListener.startAlarmListener()
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      val vibratorManager =
        context.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
      vibratorManager.defaultVibrator
    } else {
      context.getSystemService(VIBRATOR_SERVICE) as Vibrator
    }

//    val vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator
    vibrator.vibrate(4000)
    Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show()
    var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    if (alarmUri == null) {
      alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    }
    // setting default ringtone
    ringtone = RingtoneManager.getRingtone(context, alarmUri)
    // play ringtone
    ringtone.play()
  }
}
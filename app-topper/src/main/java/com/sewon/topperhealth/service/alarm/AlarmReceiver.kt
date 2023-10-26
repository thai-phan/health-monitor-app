package com.sewon.topperhealth.service.alarm

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
import com.sewon.topperhealth.MainActivity


class AlarmReceiver : BroadcastReceiver() {
  companion object {
    lateinit var vibrator: Vibrator
    lateinit var ringtone: Ringtone
  }


  override fun onReceive(context: Context, intent: Intent) {
    MainActivity.lowEnergyClient.startAlarmListener()

    vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      val vibratorManager =
        context.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
      vibratorManager.defaultVibrator
    } else {
      @Suppress("DEPRECATION")
      context.getSystemService(VIBRATOR_SERVICE) as Vibrator
    }

//    val vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator
    vibrator.vibrate(
      VibrationEffect.createWaveform(
        longArrayOf(1500, 800, 800, 800), 0
      )
    )

    var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    if (alarmUri == null) {
      alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    }
    // setting default ringtone
    ringtone = RingtoneManager.getRingtone(context, alarmUri)

    ringtone.play()

  }
}
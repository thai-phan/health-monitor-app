package com.sewon.healthmonitor.service.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.sewon.healthmonitor.MainActivity
import java.util.Calendar

class AlarmService : Service() {

  fun onCreateAlarm() {
    val alarmIntent = Intent(this, AlarmReceiver::class.java)

    MainActivity.alarmPendingIntent =
      PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_MUTABLE)

    val c = Calendar.getInstance()
    c[Calendar.HOUR_OF_DAY] = c[Calendar.HOUR_OF_DAY]
    c[Calendar.MINUTE] = c[Calendar.MINUTE]
    c[Calendar.SECOND] = c[Calendar.SECOND] + 5
//          ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), DAY, pendingIntent);
    //          ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), DAY, pendingIntent);
    MainActivity.alarmManager.set(
      AlarmManager.RTC_WAKEUP, c.timeInMillis,
      MainActivity.alarmPendingIntent
    )
  }

  fun onStopAlarm() {
    MainActivity.alarmManager.cancel(MainActivity.alarmPendingIntent)
    AlarmReceiver.ringtone.stop()
  }

  override fun onBind(intent: Intent?): IBinder? {
    TODO("Not yet implemented")
  }


}
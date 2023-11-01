package com.sewon.topperhealth.service.player

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.sewon.topperhealth.R


class BackgroundSoundService : Service() {
  lateinit var backgroundPlayer: MediaPlayer


  fun createPlayer() {
    backgroundPlayer = MediaPlayer.create(this, R.raw.bells)
    backgroundPlayer.isLooping = true // Set looping
    backgroundPlayer.setVolume(100f, 100f)
  }


  fun onUnBind(arg0: Intent?): IBinder? {
    // TO DO Auto-generated method
    return null
  }


  override fun onBind(arg0: Intent?): IBinder? {
    return null
  }

  fun onStop() {}
  fun onPause() {}
  override fun onDestroy() {
    backgroundPlayer.stop()
    backgroundPlayer.release()
  }

  override fun onLowMemory() {}
}
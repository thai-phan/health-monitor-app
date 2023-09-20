package com.sewon.healthmonitor

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.Locale


@HiltAndroidApp
class Application : Application() {
  override fun onCreate() {
    super.onCreate()

//        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("ko")
//        // Call this on the main thread as it may require Activity.restart()
//        AppCompatDelegate.setApplicationLocales(appLocale)


//        val locale = Locale("ru")
//        Locale.setDefault(locale)
//        val config: Configuration = baseContext.resources.configuration
//        config.setLocale(locale)
//        getBaseContext().getResources().configuration(config,
//            getBaseContext().getResources().getDisplayMetrics());
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
//    TODO: connect server`
}
package com.sewon.topperhealth

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


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
}
package com.sewon.topperhealth.util

import android.content.Context
import android.content.ContextWrapper
import java.util.Locale

class LanguageHelper {
  companion object {
    fun changeLanguage(context: Context, languageCode: String): ContextWrapper {
      var thisContext = context
      val resource = thisContext.resources
      val configuration = resource.configuration
      val systemLocale = configuration.locales[0]

      if (languageCode != "" && languageCode != systemLocale.language) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        configuration.setLocale(locale)
        thisContext = thisContext.createConfigurationContext(configuration)
      }
      return ContextWrapper(thisContext)
    }
  }
}
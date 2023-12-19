package com.sewon.topperhealth

import android.content.Context
import android.content.ContextWrapper
import java.util.Locale

class LanguageHelper {
  companion object {
    fun changeLanguage(context: Context, languageCode: String): ContextWrapper {
      var thisContext = context
      val resource = thisContext.resources
      val configuaration = resource.configuration
      val systemLocale = configuaration.locales[0]

      if (languageCode != "" && languageCode != systemLocale.language) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        configuaration.setLocale(locale)
        thisContext = thisContext.createConfigurationContext(configuaration)
      }
      return ContextWrapper(thisContext)
    }
  }
}
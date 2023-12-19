package com.sewon.topperhealth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


private val Context.datastore: DataStore<Preferences> by preferencesDataStore(
  name = "settings_preferences"
)

class SettingManager(private val context: Context) {
  companion object {
    private val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")
    private val SELECTED_LANGUAGE_CODE = stringPreferencesKey("selected_language_code")
  }

  suspend fun saveSelectedLanguage(appLanguage: AppLanguage) {
    context.datastore.edit { mutablePreferences ->
      mutablePreferences[SELECTED_LANGUAGE] = appLanguage.selectedLang
      mutablePreferences[SELECTED_LANGUAGE_CODE] = appLanguage.selectedLangCode
    }
  }

  private val languageFlow: Flow<AppLanguage> =
    context.datastore.data.map { preferences ->
      AppLanguage(
        preferences[SELECTED_LANGUAGE] ?: "English",
        preferences[SELECTED_LANGUAGE_CODE] ?: "en"
      )
    }

  val currentLanguage: AppLanguage
    get() = runBlocking { languageFlow.first() }

  fun observeLanguageChange(): Flow<AppLanguage> {
    return languageFlow
  }
}

data class AppLanguage(val selectedLang: String, val selectedLangCode: String)
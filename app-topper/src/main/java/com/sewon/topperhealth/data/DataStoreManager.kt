package com.sewon.topperhealth.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sewon.topperhealth.service.algorithm.sleep.AlgorithmConstants.REF_COUNT
import com.sewon.topperhealth.util.AppLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class DataStoreManager(private var context: Context) {
  companion object {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
    private val IS_TERM_ACCEPTED = booleanPreferencesKey("is_term_accepted")
    private val IS_LOG_SHOWED = booleanPreferencesKey("is_show_log")
    private val IS_DISABLE_DIM = booleanPreferencesKey("is_disable_dim")
    private val REFERENCE_COUNT = intPreferencesKey("reference_count")
    private val OPENAI_KEY = stringPreferencesKey("openai_key")
    private val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")
    private val SELECTED_LANGUAGE_CODE = stringPreferencesKey("selected_language_code")
  }

  suspend fun saveSelectedLanguage(appLanguage: AppLanguage) {
    context.dataStore.edit { mutablePreferences ->
      mutablePreferences[SELECTED_LANGUAGE] = appLanguage.selectedLang
      mutablePreferences[SELECTED_LANGUAGE_CODE] = appLanguage.selectedLangCode
    }
  }

  private val languageFlow: Flow<AppLanguage> =
    context.dataStore.data.map { preferences ->
      AppLanguage(
        preferences[SELECTED_LANGUAGE] ?: "한국어",
        preferences[SELECTED_LANGUAGE_CODE] ?: "ko"
      )
    }

  val currentLanguage: AppLanguage
    get() = runBlocking { languageFlow.first() }

  fun observeLanguageChange(): Flow<AppLanguage> {
    Timber.tag("SettingManager").d("observeLanguageChange")
    return languageFlow
  }

  suspend fun saveAcceptTerm(value: Boolean) {
    context.dataStore.edit { preferences -> preferences[IS_TERM_ACCEPTED] = value }
  }

  val isTermAccepted: Flow<Boolean> = context.dataStore.data.map { preferences ->
    preferences[IS_TERM_ACCEPTED] ?: false
  }

  suspend fun saveShowLog(value: Boolean) {
    context.dataStore.edit { preferences -> preferences[IS_LOG_SHOWED] = value }
  }

  val isLogShowed: Flow<Boolean> = context.dataStore.data.map { preferences ->
    preferences[IS_LOG_SHOWED] ?: false
  }

  suspend fun saveDisableDim(value: Boolean) {
    context.dataStore.edit { preferences -> preferences[IS_DISABLE_DIM] = value }
  }

  val isDimDisabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
    preferences[IS_DISABLE_DIM] ?: false
  }

  suspend fun saveReferenceCount(value: Int) {
    context.dataStore.edit { preferences -> preferences[REFERENCE_COUNT] = value }
  }

  val referenceCount: Flow<Int> = context.dataStore.data.map { preferences ->
    preferences[REFERENCE_COUNT] ?: REF_COUNT
  }

  suspend fun saveOpenAIKey(value: String) {
    context.dataStore.edit { preferences -> preferences[OPENAI_KEY] = value }
  }

  val openAIKey: Flow<String> = context.dataStore.data.map { preferences ->
    preferences[OPENAI_KEY] ?: ""
  }
}



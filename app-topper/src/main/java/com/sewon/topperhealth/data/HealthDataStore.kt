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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HealthDataStore(private var context: Context) {
  companion object {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
    private val TOPPER_LANGUAGE = stringPreferencesKey("topper_language")
    private val IS_TERM_ACCEPTED = booleanPreferencesKey("is_term_accepted")
    private val IS_LOG_SHOWED = booleanPreferencesKey("is_show_log")
    private val IS_DISABLE_DIM = booleanPreferencesKey("is_disable_dim")
    private val REFERENCE_COUNT = intPreferencesKey("reference_count")
    private val OPENAI_KEY = stringPreferencesKey("openai_key")


  }

  suspend fun changeLanguage(language: String) {
    context.dataStore.edit { preferences -> preferences[TOPPER_LANGUAGE] = language }
  }

  val getLanguage: Flow<String> = context.dataStore.data.map { preferences ->
    preferences[TOPPER_LANGUAGE] ?: "ko"
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
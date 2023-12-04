package com.sewon.topperhealth.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HealthDataStore(private var context: Context) {
  companion object {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
    private val IS_TERM_ACCEPTED = booleanPreferencesKey("is_term_accepted")
    private val IS_LOG_SHOWED = booleanPreferencesKey("is_show_log")
    private val IS_SCREEN_DIMMED = booleanPreferencesKey("is_screen_dim")
  }

  suspend fun saveAcceptTerm(value: Boolean) {
    context.dataStore.edit { preferences -> preferences[IS_TERM_ACCEPTED] = value }
  }

  val doAcceptTerm: Flow<Boolean> = context.dataStore.data.map { preferences ->
    preferences[IS_TERM_ACCEPTED] ?: false
  }

  suspend fun saveShowLog(value: Boolean) {
    context.dataStore.edit { preferences -> preferences[IS_LOG_SHOWED] = value }
  }

  val doShowLog: Flow<Boolean> = context.dataStore.data.map { preferences ->
    preferences[IS_LOG_SHOWED] ?: false
  }

  suspend fun saveDimScreen(value: Boolean) {
    context.dataStore.edit { preferences -> preferences[IS_SCREEN_DIMMED] = value }
  }

  val doDimScreen: Flow<Boolean> = context.dataStore.data.map { preferences ->
    preferences[IS_SCREEN_DIMMED] ?: false
  }


}
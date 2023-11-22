package com.sewon.topperhealth.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HealthDataStore(private var context: Context) {
  companion object {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
    private val TERM_AGREEMENT_ACCEPTED = booleanPreferencesKey("term_agreement_accepted")
  }

  val getIsTermAgreementAccepted: Flow<Boolean> = context.dataStore.data.map { preferences ->
    preferences[TERM_AGREEMENT_ACCEPTED] ?: false
  }

  suspend fun acceptTermAgreement(isAccept: Boolean) {
    context.dataStore.edit { preferences -> preferences[TERM_AGREEMENT_ACCEPTED] = isAccept }
  }
}
package com.sewon.healthmonitor.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDataStore(private var context: Context) {
//    // At the top level of your kotlin file:
//    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        private val TERM_AGREEMENT_ACCEPTED = booleanPreferencesKey("term_agreement_accepted")
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token")
    }

    val getAccessToken: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_TOKEN_KEY] ?: ""
    }

    val getIsTermAgreementAccepted: Flow<Boolean> = context.dataStore.data.map {
        preferences -> preferences[TERM_AGREEMENT_ACCEPTED] ?: false
    }

    suspend fun acceptTermAgreement(isAccept: Boolean) {
        context.dataStore.edit { preferences -> preferences[TERM_AGREEMENT_ACCEPTED] = isAccept  }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
        }
    }
}
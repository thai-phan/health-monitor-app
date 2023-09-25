/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp.di

import android.content.Context
import androidx.room.Room
import com.sewon.healthmonitor.data.HealthDatabase
import com.sewon.healthmonitor.data.source.local.dao.LocalSensorDao
import com.sewon.healthmonitor.data.source.local.dao.LocalUserDao
import com.sewon.healthmonitor.data.source.local.dao.LocalSettingDao
import com.sewon.healthmonitor.data.repository.SensorRepository
import com.sewon.healthmonitor.data.repository.SessionRepository
import com.sewon.healthmonitor.data.repository.SettingRepository
import com.sewon.healthmonitor.data.repository.repointerface.ISensorRepository
import com.sewon.healthmonitor.data.repository.repointerface.ISessionRepository
import com.sewon.healthmonitor.data.repository.repointerface.ISettingRepository
import com.sewon.healthmonitor.data.source.local.dao.LocalSessionDao

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Singleton
  @Provides
  fun provideDataBase(@ApplicationContext context: Context): HealthDatabase {
    return Room.databaseBuilder(
      context.applicationContext,
      HealthDatabase::class.java,
      "HealthMonitor.db"
    ).build()
  }

  @Provides
  fun provideSensorDao(database: HealthDatabase): LocalSensorDao = database.sensorDao()

  @Provides
  fun provideUserSettingDao(database: HealthDatabase): LocalSettingDao = database.settingDao()

  @Provides
  fun provideUserDao(database: HealthDatabase): LocalUserDao = database.userDao()

  @Provides
  fun provideSessionDao(database: HealthDatabase): LocalSessionDao = database.sessionDao()
}


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

  @Singleton
  @Binds
  abstract fun bindRadarRepository(radarRepository: SensorRepository): ISensorRepository


  @Singleton
  @Binds
  abstract fun bindSettingRepository(settingRepository: SettingRepository): ISettingRepository


  @Singleton
  @Binds
  abstract fun bindSessionRepository(sessionRepository: SessionRepository): ISessionRepository
}

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class SettingRepositoryModule {
//
//    @Singleton
//    @Binds
//    abstract fun bindUserSettingRepository(userSettingRepository: UserSettingRepository): IUserSettingRepository
//
//}


//@Module
//@InstallIn(SingletonComponent::class)
//abstract class DataSourceModule {
//
//    @Singleton
//    @Binds
//    abstract fun bindNetworkDataSource(dataSource: TaskNetworkDataSource): NetworkDataSource
//}


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
import com.sewon.topperhealth.data.HealthDatabase
import com.sewon.topperhealth.data.source.local.dao.LocalTopperDao
import com.sewon.topperhealth.data.source.local.dao.LocalUserDao
import com.sewon.topperhealth.data.source.local.dao.LocalSettingDao
import com.sewon.topperhealth.data.source.local.repository.TopperRepository
import com.sewon.topperhealth.data.source.local.repository.SessionRepository
import com.sewon.topperhealth.data.source.local.repository.SettingRepository
import com.sewon.topperhealth.data.source.local.repository.UserRepository
import com.sewon.topperhealth.data.irepository.ITopperRepository
import com.sewon.topperhealth.data.irepository.ISessionRepository
import com.sewon.topperhealth.data.irepository.ISettingRepository
import com.sewon.topperhealth.data.irepository.IUserRepository
import com.sewon.topperhealth.data.source.local.dao.LocalSessionDao

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
  fun provideSensorDao(database: HealthDatabase): LocalTopperDao = database.sensorDao()

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
  abstract fun bindTopperRepository(topperRepository: TopperRepository): ITopperRepository


  @Singleton
  @Binds
  abstract fun bindSettingRepository(settingRepository: SettingRepository): ISettingRepository


  @Singleton
  @Binds
  abstract fun bindSessionRepository(sessionRepository: SessionRepository): ISessionRepository


  @Singleton
  @Binds
  abstract fun bindUserRepository(userRepository: UserRepository): IUserRepository

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


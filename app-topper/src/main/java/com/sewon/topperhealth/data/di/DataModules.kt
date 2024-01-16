package com.sewon.topperhealth.data.di

import android.content.Context
import androidx.room.Room
import com.sewon.topperhealth.data.DatabaseManager
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
  fun provideDataBase(@ApplicationContext context: Context): DatabaseManager {
    return Room.databaseBuilder(
      context.applicationContext,
      DatabaseManager::class.java,
      "TopperHealth.db"
    ).build()
  }

  @Provides
  fun provideSensorDao(database: DatabaseManager): LocalTopperDao = database.sensorDao()

  @Provides
  fun provideUserSettingDao(database: DatabaseManager): LocalSettingDao = database.settingDao()

  @Provides
  fun provideUserDao(database: DatabaseManager): LocalUserDao = database.userDao()

  @Provides
  fun provideSessionDao(database: DatabaseManager): LocalSessionDao = database.sessionDao()
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


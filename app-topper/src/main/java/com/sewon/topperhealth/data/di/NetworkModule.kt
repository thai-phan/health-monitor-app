package com.sewon.topperhealth.data.di

import com.sewon.topperhealth.data.source.server.UnsplashService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

  @Singleton
  @Provides
  fun provideUnsplashService(): UnsplashService {
    return UnsplashService.create()
  }
}

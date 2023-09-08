/*
 * Copyright 2020 Google LLC
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

package com.sewon.healthmonitor.api

import com.sewon.healthmonitor.BuildConfig
import com.sewon.healthmonitor.data.source.server.ServerResponse
import com.sewon.healthmonitor.data.source.server.UnsplashSearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Used to connect to the Unsplash API to fetch photos
 */
interface ServerService {
  @GET("/quotes?page=1")
  suspend fun testServer(): ServerResponse

  @GET("search/photos")
  suspend fun searchPhotos(
    @Query("query") query: String,
    @Query("page") page: Int,
    @Query("per_page") perPage: Int,
//        @Query("client_id") clientId: String = BuildConfig.UNSPLASH_ACCESS_KEY
  ): UnsplashSearchResponse

  companion object {
    private const val BASE_URL = "https://quotable.io/"

    fun create(): ServerService {
      val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

      val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

      return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ServerService::class.java)
    }
  }
}
package com.sewon.topperhealth.api

import com.sewon.topperhealth.data.source.server.ServerResponse
import com.sewon.topperhealth.data.source.server.UnsplashSearchResponse
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
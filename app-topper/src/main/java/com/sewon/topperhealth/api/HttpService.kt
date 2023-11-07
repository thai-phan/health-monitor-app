package com.sewon.topperhealth.api

import com.sewon.topperhealth.data.source.server.HttpResponse
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
interface HttpService {
  @GET("/")
  suspend fun testServer(): HttpResponse

  @GET("search/photos")
  suspend fun searchPhotos(
    @Query("query") query: String,
    @Query("page") page: Int,
    @Query("per_page") perPage: Int,
//        @Query("client_id") clientId: String = BuildConfig.UNSPLASH_ACCESS_KEY
  ): UnsplashSearchResponse

  companion object {
    private const val BASE_URL = "http://221.145.170.183:3000/"

    fun create(): HttpService {
      val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

      val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

      return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HttpService::class.java)
    }
  }
}
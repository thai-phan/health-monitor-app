package com.sewon.topperhealth.data.source.server

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface UnsplashService {
  @GET("/")
  suspend fun testServer(): ServerResponse


  @GET("search/photos")
  suspend fun searchPhotos(
    @Query("query") query: String,
    @Query("page") page: Int,
    @Query("per_page") perPage: Int,
//        @Query("client_id") clientId: String = BuildConfig.UNSPLASH_ACCESS_KEY
  ): UnsplashSearchResponse

  companion object {
    private const val BASE_URL = "https://api.unsplash.com/"

    fun create(): UnsplashService {
      val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

      val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

      return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UnsplashService::class.java)
    }
  }
}
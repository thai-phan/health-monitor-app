package com.sewon.topperhealth.api.openai

import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ServiceOpenAI {
  companion object {
    private const val BASE_URL = "https://api.openai.com/"


    fun create(key: String): ServiceOpenAI {
      val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

      val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(Interceptor { chain ->
          val newRequest: Request = chain.request().newBuilder()
            .addHeader(
              "Authorization",
              "Bearer $key"
            )
            .addHeader("Content-Type", "application/json")
            .build()
          chain.proceed(newRequest)
        })
        .build()

      return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ServiceOpenAI::class.java)
    }
  }

  @POST("/v1/chat/completions")
  suspend fun getSleepAdvice(@Body body: RequestBodySleepAdvice): ResBodySleepAdvice
}
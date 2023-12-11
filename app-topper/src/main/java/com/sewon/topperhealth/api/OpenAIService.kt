package com.sewon.topperhealth.api

import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OpenAIService {
  companion object {
    private const val BASE_URL = "https://api.openai.com/"


    fun create(): OpenAIService {
      val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

      val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
          .addHeader("Authorization", "Bearer ")
          .addHeader("Content-Type", "application/json")
          .build()
        chain.proceed(newRequest)
      }).build()


      return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OpenAIService::class.java)
    }
  }

  @POST("/v1/chat/completions")
  suspend fun getSleepAdvice(@Body body: RequestBodySleepAdvice): ResBodySleepAdvice
}
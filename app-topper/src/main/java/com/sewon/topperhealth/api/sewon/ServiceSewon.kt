package com.sewon.topperhealth.api.sewon

import com.google.gson.GsonBuilder
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


interface ServiceSewon {
  companion object {
    private const val BASE_URL = "http://175.196.118.115:8080/"


    fun create(): ServiceSewon {
      val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

      val gson = GsonBuilder()
        .setLenient()
        .create()

      val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(600, TimeUnit.SECONDS)
        .readTimeout(600, TimeUnit.SECONDS)
        .build()

      return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ServiceSewon::class.java)
    }
  }

  @POST("/predict")
  suspend fun getAlgorithm(@Body body: RequestBody): ResponseBody
}
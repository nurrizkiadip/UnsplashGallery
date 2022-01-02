package com.nurrizkiadip.unsplashgallery.data.source.remote.network

import android.app.Application
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import com.nurrizkiadip.unsplashgallery.utils.UnsplashGalleryApp

class ApiConfig {
  companion object {
    fun getApiService(application: Application): ApiService {
      val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
      val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

      val retrofit = Retrofit.Builder()
        .baseUrl((application as UnsplashGalleryApp).getBaseUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

      return retrofit.create(ApiService::class.java)
    }
  }
}
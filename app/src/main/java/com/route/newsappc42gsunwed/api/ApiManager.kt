package com.route.newsappc42gsunwed.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {
    private val httpLoggingInterceptor = HttpLoggingInterceptor {
        Log.e("API", it)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    class ApiKeyInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val newURLBuilder = chain.request().url.newBuilder()
            val newRequestBuilder = chain.request().newBuilder()
            newURLBuilder.addQueryParameter("apiKey", "c027443ca9624422bfbe9b160b9ec11a")
            newRequestBuilder.url(newURLBuilder.build())
            return chain.proceed(newRequestBuilder.build())
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor())
        .addInterceptor(httpLoggingInterceptor)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getNewsService(): NewsService {
        return retrofit.create(NewsService::class.java)
    }

}
package com.coding.tawktoexam.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    fun getGitHubService(): Endpoint {
        return getGitHubRetrofit().create(Endpoint::class.java)
    }

   private fun getGitHubRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(getOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        var client = OkHttpClient.Builder().apply {
            this.writeTimeout(30, TimeUnit.SECONDS)
            this.connectTimeout(30, TimeUnit.SECONDS)
            this.readTimeout(30, TimeUnit.SECONDS)
            this.addInterceptor(getHttpLogger())
        }
        return client.build()
    }

    private fun getHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}
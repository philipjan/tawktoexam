package com.coding.tawktoexam.http

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkModule(val app: Application) {

    private fun hasNetwork(): Boolean {
        var isConnected = false
        val cm = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

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
        val cacheSize = (5.times(1024).times(1024)).toLong()
        val cache = Cache(app.cacheDir, cacheSize)
        var client = OkHttpClient.Builder().apply {
            this.writeTimeout(30, TimeUnit.SECONDS)
            this.connectTimeout(30, TimeUnit.SECONDS)
            this.readTimeout(30, TimeUnit.SECONDS)
            this.cache(cache)
            this.addInterceptor(getHttpLogger())
            this.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request = chain.request()
                    request = if (hasNetwork()) {
                        request.newBuilder()
                            .header("Cache-Control", "public, max-age=" + 60)
                            .build()
                    } else {
                        request.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                            .build()
                    }
                   return chain.proceed(request)
                }
            })
        }
        return client.build()
    }

    private fun getHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}
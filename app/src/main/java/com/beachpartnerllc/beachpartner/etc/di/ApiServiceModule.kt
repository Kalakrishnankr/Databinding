package com.beachpartnerllc.beachpartner.etc.di

import android.app.Application
import com.beachpartnerllc.beachpartner.etc.model.ApiService
import com.beachpartnerllc.beachpartner.etc.model.HeaderInterceptor
import com.beachpartnerllc.beachpartner.etc.model.Preference
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 29 Nov 2017 at 11:29 AM
 */
@Module
class ApiServiceModule {
    @Provides
    @Singleton
    fun okHttpCacheProvider(app: Application): Cache {
        val cacheSize = 50 * 1024 * 1024L // 50 MiB
        return Cache(app.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun gsonProvider(): Gson {
        val gsonBuilder = GsonBuilder().setDateFormat("yyyy-MM-dd")
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun okHttpClientProvider(cache: Cache, pref: Preference, app: Application): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(HeaderInterceptor(app, pref))
                .addInterceptor(logger)
                .build()
    }

    @Provides
    @Singleton
    fun retrofitProvider(gson: Gson, okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(ApiService.URL_BASE)
                .client(okHttpClient)
                .build()
                .create(ApiService::class.java)
    }
}
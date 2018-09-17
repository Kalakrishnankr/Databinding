package com.beachpartnerllc.beachpartner.etc.model.rest.mock

import android.app.Application
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiService
import com.beachpartnerllc.beachpartner.etc.model.rest.LiveDataCallAdapterFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 05 Jun 2018 at 9:38 AM
 */
@Module
class MockServiceModule {
    @Provides
    @Singleton
    fun serializerProvider(): Gson {
        return GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(serializer: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(ApiService.URL_BASE)
                .client(OkHttpClient())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(serializer))
                .build()
    }

    @Provides
    @Singleton
    fun behaviorProvider(): NetworkBehavior {
        val behavior = NetworkBehavior.create()
        behavior.setDelay(3, TimeUnit.SECONDS)
        behavior.setErrorPercent(30)
        behavior.setVariancePercent(75)
        return behavior
    }

    @Provides
    @Singleton
    fun provideMockService(
            retrofit: Retrofit,
            behavior: NetworkBehavior,
            serializer: Gson,
            app: Application): ApiService {
        val mock = MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build()
        val delegate = mock.create(ApiService::class.java)
        return MockService(delegate, serializer, app)
    }
}
package com.beachpartnerllc.beachpartner.etc.di

import com.beachpartnerllc.beachpartner.etc.model.ApiService
import com.beachpartnerllc.beachpartner.etc.model.MockService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 05 Jun 2018 at 9:38 AM
 */
@Module
class ApiMockServiceModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(ApiService.URL_BASE)
                .build()
    }

    @Provides
    @Singleton
    fun behaviorProvider(): NetworkBehavior {
        val behavior = NetworkBehavior.create()
        behavior.setDelay(10, TimeUnit.SECONDS)
        behavior.setVariancePercent(80)
        return behavior
    }

    @Provides
    @Singleton
    fun provideMockService(retrofit: Retrofit, behavior: NetworkBehavior): ApiService {
        val mock = MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build()
        val delegate = mock.create(ApiService::class.java)
        return MockService(delegate)
    }
}
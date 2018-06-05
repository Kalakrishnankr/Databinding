package com.beachpartnerllc.beachpartner.etc.di

import android.app.Application
import com.beachpartnerllc.beachpartner.etc.App
import com.beachpartnerllc.beachpartner.etc.model.ApiMockServiceModule

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ViewModelModule::class,
    ActivityModule::class,
    ApiMockServiceModule::class,
    SharedPreferenceModule::class
])
interface AppComponent {
    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}

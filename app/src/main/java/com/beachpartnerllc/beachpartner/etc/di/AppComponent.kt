package com.beachpartnerllc.beachpartner.etc.di

import android.app.Application
import com.beachpartnerllc.beachpartner.etc.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
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

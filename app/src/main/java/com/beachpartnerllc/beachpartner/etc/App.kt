package com.beachpartnerllc.beachpartner.etc

import android.app.Activity
import androidx.multidex.MultiDexApplication
import com.beachpartnerllc.beachpartner.BuildConfig
import com.beachpartnerllc.beachpartner.etc.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 29 Nov 2017 at 10:50 AM
 */

class App : MultiDexApplication(), HasActivityInjector {
    @Inject
    lateinit var injector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // TODO: Plant a production tree for crash analytics
        }
    }

    override fun activityInjector() = injector
}
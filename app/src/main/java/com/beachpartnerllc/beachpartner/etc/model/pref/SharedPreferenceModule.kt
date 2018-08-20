package com.beachpartnerllc.beachpartner.etc.model.pref

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 05 Dec 2017 at 11:35 AM
 */

@Module
class SharedPreferenceModule {
    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): Preference {
        val pref = app.getSharedPreferences(Preference.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return Preference(pref)
    }
}
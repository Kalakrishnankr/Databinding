package com.beachpartnerllc.beachpartner.home

import com.beachpartnerllc.beachpartner.finder.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface HomeFragmentModule {
    @ContributesAndroidInjector
    fun contributeAthleteFragment(): AthleteHomeFragment
    
    @ContributesAndroidInjector
    fun contributeSearchFragment() : SearchFragment
}
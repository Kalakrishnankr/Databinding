package com.beachpartnerllc.beachpartner.home

import com.beachpartnerllc.beachpartner.finder.ProfileDetailedInfoFragment
import com.beachpartnerllc.beachpartner.finder.SearchFragment
import com.beachpartnerllc.beachpartner.finder.SearchResultFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface HomeFragmentModule {
    @ContributesAndroidInjector
    fun contributeAthleteFragment(): AthleteHomeFragment
    
    @ContributesAndroidInjector
    fun contributeSearchFragment() : SearchFragment
    
    @ContributesAndroidInjector
    fun contributeSearchResultFragment() : SearchResultFragment
    
    @ContributesAndroidInjector
    fun contributeProfileDetailedInfoFragment() : ProfileDetailedInfoFragment
}
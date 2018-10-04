package com.beachpartnerllc.beachpartner.home

import com.beachpartnerllc.beachpartner.finder.*
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
	fun contributeCardFragment() : CardFragment
	
	@ContributesAndroidInjector
	fun contributeStripFragment() : StripFragment
    
    @ContributesAndroidInjector
    fun contributeProfileDetailedInfoFragment() : ProfileDetailFragment
}
package com.beachpartnerllc.beachpartner.home

import com.beachpartnerllc.beachpartner.user.profile.AthleteProfileFragment
import com.beachpartnerllc.beachpartner.user.profile.BasicInfoFragment
import com.beachpartnerllc.beachpartner.user.profile.MoreInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface HomeFragmentModule {
    @ContributesAndroidInjector
    fun contributeAthleteFragment(): AthleteHomeFragment
    
    @ContributesAndroidInjector
    fun contributeBasicInfoFragment(): BasicInfoFragment
    
    @ContributesAndroidInjector
    fun contributeMoreInfoFragment(): MoreInfoFragment
    
    @ContributesAndroidInjector
    fun contributeAthleteProfileFragment(): AthleteProfileFragment
}
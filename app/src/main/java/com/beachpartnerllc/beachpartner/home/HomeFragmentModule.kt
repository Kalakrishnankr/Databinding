package com.beachpartnerllc.beachpartner.home

import com.beachpartnerllc.beachpartner.user.profile.*
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
	
	@ContributesAndroidInjector
	fun contributeCoachProfileFragment(): CoachProfileFragment
	
	@ContributesAndroidInjector
	fun contributeCoachMoreInfoFragment(): CoachMoreInfoFragment
}
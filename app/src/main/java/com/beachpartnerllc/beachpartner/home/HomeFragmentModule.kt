package com.beachpartnerllc.beachpartner.home

import com.beachpartnerllc.beachpartner.event.CalendarFragment
import com.beachpartnerllc.beachpartner.event.MasterCalendarFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface HomeFragmentModule {
    @ContributesAndroidInjector
    fun contributeAthleteFragment(): AthleteHomeFragment

    @ContributesAndroidInjector
    fun contributeCalendarFragment(): CalendarFragment

    @ContributesAndroidInjector
    fun contributeMasterCalendarFragment(): MasterCalendarFragment
}
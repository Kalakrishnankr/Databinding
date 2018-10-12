package com.beachpartnerllc.beachpartner.home

import com.beachpartnerllc.beachpartner.connection.ConnectionFragment
import com.beachpartnerllc.beachpartner.connection.InviteConnectionFragment
import com.beachpartnerllc.beachpartner.event.CalendarFragment
import com.beachpartnerllc.beachpartner.event.EventFragment
import com.beachpartnerllc.beachpartner.event.MasterCalendarFragment
import com.beachpartnerllc.beachpartner.finder.*
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

    @ContributesAndroidInjector
    fun contributeEventFragment(): EventFragment

    @ContributesAndroidInjector
    fun contributeConnectionFragment(): ConnectionFragment

    @ContributesAndroidInjector
    fun contributeInviteConnectionFragment(): InviteConnectionFragment

    @ContributesAndroidInjector
    fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    fun contributeSearchResultFragment(): SearchResultFragment

    @ContributesAndroidInjector
    fun contributeCardFragment(): CardFragment

    @ContributesAndroidInjector
    fun contributeStripFragment(): StripFragment

    @ContributesAndroidInjector
    fun contributeProfileDetailedInfoFragment(): ProfileDetailFragment
}
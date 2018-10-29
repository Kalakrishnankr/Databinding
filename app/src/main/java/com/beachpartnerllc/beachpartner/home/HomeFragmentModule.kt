package com.beachpartnerllc.beachpartner.home

import com.beachpartnerllc.beachpartner.connection.ConnectionFragment
import com.beachpartnerllc.beachpartner.connection.InviteConnectionFragment
import com.beachpartnerllc.beachpartner.event.CalendarFragment
import com.beachpartnerllc.beachpartner.event.EventFragment
import com.beachpartnerllc.beachpartner.event.MasterCalendarFragment
import com.beachpartnerllc.beachpartner.event.UpcomingTournamentFragment
import com.beachpartnerllc.beachpartner.finder.*
import com.beachpartnerllc.beachpartner.messaging.ChatFragment
import com.beachpartnerllc.beachpartner.messaging.MessageFragment
import com.beachpartnerllc.beachpartner.messaging.RecentChatFragment
import com.beachpartnerllc.beachpartner.user.profile.*
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
    fun contributeStripFragment(): BlueBpStripFragment

    @ContributesAndroidInjector
    fun contributeProfileDetailedInfoFragment(): ProfileDetailFragment

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

    @ContributesAndroidInjector
    fun contributeChatFragment(): ChatFragment

    @ContributesAndroidInjector
    fun contributeMessageFragment(): MessageFragment

    @ContributesAndroidInjector
    fun contributeUpcomingEventFragment(): UpcomingTournamentFragment

    @ContributesAndroidInjector
    fun contributeRecentFragment(): RecentChatFragment
}
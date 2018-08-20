package com.beachpartnerllc.beachpartner.etc.di


import com.beachpartnerllc.beachpartner.home.HomeActivity
import com.beachpartnerllc.beachpartner.home.HomeFragmentModule
import com.beachpartnerllc.beachpartner.init.MainActivity
import com.beachpartnerllc.beachpartner.user.auth.AuthActivity
import com.beachpartnerllc.beachpartner.user.auth.AuthFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 06 Jan 2018 at 12:10 PM
 */
@Module
interface ActivityModule {
    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [AuthFragmentModule::class])
    fun contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    fun contributeHomeActivity(): HomeActivity
}

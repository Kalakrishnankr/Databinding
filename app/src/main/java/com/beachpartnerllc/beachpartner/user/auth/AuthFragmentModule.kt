package com.beachpartnerllc.beachpartner.user.auth

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 31 May 2018 at 1:47 PM
 */
@Module
interface AuthFragmentModule {
    @ContributesAndroidInjector
    fun contributeSignInFragment(): SignInFragment

    @ContributesAndroidInjector
    fun contributeSignUpFragment(): SignUpFragment

    @ContributesAndroidInjector
    fun contributeSignUp2Fragment(): SignUp2Fragment
}
package com.beachpartnerllc.beachpartner.etc.di

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {
    /*@Binds
    @IntoMap
    @ViewModelKey(LeaderViewModel::class)
    fun bindLeaderViewModel(viewModel: LeaderViewModel): ViewModel*/

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

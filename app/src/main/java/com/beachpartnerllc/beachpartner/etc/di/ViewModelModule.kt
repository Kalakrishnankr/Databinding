package com.beachpartnerllc.beachpartner.etc.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.user.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun bindLeaderViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

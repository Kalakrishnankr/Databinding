package com.beachpartnerllc.beachpartner.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.HomeActivityBinding
import com.beachpartnerllc.beachpartner.etc.common.BaseActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class HomeActivity : BaseActivity(), HasSupportFragmentInjector {
    @Inject lateinit var injector: DispatchingAndroidInjector<Fragment>
    private lateinit var binding: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val navController = findNavController(R.id.navFragment)
        setupActionBarWithNavController(navController)
        binding.homeBNV.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.navFragment).navigateUp()

    override fun supportFragmentInjector() = injector
}

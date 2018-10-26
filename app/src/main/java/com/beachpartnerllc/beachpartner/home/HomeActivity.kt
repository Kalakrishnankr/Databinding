package com.beachpartnerllc.beachpartner.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.amazonaws.mobile.client.AWSMobileClient
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.HomeActivityBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseActivity


class HomeActivity : BaseActivity() {
    private lateinit var binding: HomeActivityBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AWSMobileClient.getInstance().initialize(this).execute()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        setSupportActionBar(binding.toolbar)
        navController = findNavController(R.id.navFragment)
        setupWithNavController(binding.toolbar, navController)
        setupActionBarWithNavController(this, navController)
        setupWithNavController(binding.homeBNV, navController)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}

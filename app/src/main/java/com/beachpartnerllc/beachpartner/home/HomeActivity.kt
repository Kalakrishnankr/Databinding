package com.beachpartnerllc.beachpartner.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.*
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.HomeActivityBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseActivity

class HomeActivity : BaseActivity() {
    private lateinit var binding: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.navFragment)
        setupWithNavController(binding.toolbar, navController)
        setupActionBarWithNavController(this, navController)
	    setupWithNavController(binding.homeBNV, navController)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.navFragment).navigateUp()
}

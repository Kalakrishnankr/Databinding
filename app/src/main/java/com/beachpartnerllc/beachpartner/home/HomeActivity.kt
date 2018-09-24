package com.beachpartnerllc.beachpartner.home

import android.os.Bundle
import android.view.Menu
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
        val navController = findNavController(R.id.navFragment)
	    setupActionBarWithNavController(this, navController)
	    setupWithNavController(binding.homeBNV, navController)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.navFragment).navigateUp()
	
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		val inflater = menuInflater
		inflater.inflate(R.menu.menu_save_discard,menu)
		return super.onCreateOptionsMenu(menu)
	}
}

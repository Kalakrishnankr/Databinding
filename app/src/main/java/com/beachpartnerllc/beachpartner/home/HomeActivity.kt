package com.beachpartnerllc.beachpartner.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.*
import com.amazonaws.mobile.client.AWSMobileClient
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.HomeActivityBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseActivity
import com.beachpartnerllc.beachpartner.etc.model.pref.Preference
import javax.inject.Inject

class HomeActivity : BaseActivity() {
	@Inject lateinit var preferences: Preference
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
	
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		super.onCreateOptionsMenu(menu)
		menuInflater.inflate(R.menu.extra_menu, menu)
		if (preferences.userType!!.isAthlete()) {
			menu!!.add(Menu.NONE, R.id.action_profile_athlete, 50, "Profile")
		} else menu!!.add(Menu.NONE, R.id.action_profile_coach, 50, "Profile")
		return true
	}
	
	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		return when (item!!.itemId) {
			R.id.action_discard -> false
			
			R.id.action_save -> false
			
			else -> {
				navController.navigate(item.itemId)
				true
			}
		}
	}
	
	override fun onSupportNavigateUp() = findNavController(R.id.navFragment).navigateUp()
}
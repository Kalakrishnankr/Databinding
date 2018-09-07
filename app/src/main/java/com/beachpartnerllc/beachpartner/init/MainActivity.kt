package com.beachpartnerllc.beachpartner.init

import android.os.Bundle
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.etc.base.BaseActivity
import com.beachpartnerllc.beachpartner.etc.common.startActivity
import com.beachpartnerllc.beachpartner.user.auth.AuthActivity

class MainActivity : BaseActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		startActivity<AuthActivity>()
		finish()
	}
}

package com.beachpartnerllc.beachpartner.init

import android.content.Intent
import android.os.Bundle
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.etc.base.BaseActivity
import com.beachpartnerllc.beachpartner.etc.common.startActivity
import com.beachpartnerllc.beachpartner.home.HomeActivity
import com.beachpartnerllc.beachpartner.user.auth.AuthActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity<AuthActivity>(isForResult = true, requestCode = RC_AUTH)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == RC_AUTH) {
            startActivity<HomeActivity>()
        }

        finish()
    }

    companion object {
        private const val RC_AUTH = 1
    }
}

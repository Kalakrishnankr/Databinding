package com.beachpartnerllc.beachpartner.user.auth

import android.os.Bundle
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.etc.common.BaseActivity

class AuthActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.containerFL, SignInFragment())
                    .commit()
        }
    }
}

package com.beachpartnerllc.beachpartner.user.auth

import android.os.Bundle
import androidx.navigation.Navigation.*
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.etc.base.BaseActivity

class AuthActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.authNHF).navigateUp()
}

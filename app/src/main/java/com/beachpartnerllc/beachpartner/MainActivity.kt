package com.beachpartnerllc.beachpartner

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.beachpartnerllc.beachpartner.etc.common.startActivity
import com.beachpartnerllc.beachpartner.user.auth.AuthActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity<AuthActivity>()
    }
}

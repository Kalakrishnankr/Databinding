package com.beachpartnerllc.beachpartner.user.auth

import android.os.Bundle
import android.support.v4.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.etc.common.BaseActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class AuthActivity : BaseActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var mInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.authNHF).navigateUp()

    override fun supportFragmentInjector() = mInjector
}

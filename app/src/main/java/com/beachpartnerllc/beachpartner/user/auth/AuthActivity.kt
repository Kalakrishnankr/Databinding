package com.beachpartnerllc.beachpartner.user.auth

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.*
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.etc.base.BaseActivity
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.user.auth.AuthState.*
import javax.inject.Inject

class AuthActivity : BaseActivity() {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        navController = findNavController(this, R.id.authNHF)

        val vm: AuthViewModel = getViewModel(factory)
        vm.state.observe(this, Observer { authStateChanged(it) })
    }

    private fun authStateChanged(state: AuthState) {
        when (state) {
            REGISTERED -> PostRegistrationFragment().show(supportFragmentManager, null)

            SIGN_IN -> navController.navigate(R.id.action_sign_in)

            UNVERIFIED -> PostRegistrationFragment.newInstance().show(supportFragmentManager, null)

            AUTHENTICATED -> {
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}

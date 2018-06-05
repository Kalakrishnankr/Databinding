package com.beachpartnerllc.beachpartner.user.auth

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Jun 2018 at 2:44 PM
 */
class AuthViewModel @Inject constructor(private val mRepo: AuthRepository) : ViewModel() {
    val auth = MutableLiveData<Auth>()
    val state = MutableLiveData<AuthState>()
    val validate = MutableLiveData<Boolean>()

    fun onSignIn() {
        mRepo.signIn(auth.value!!, state)
    }

    fun skipInitCount(): Long = if (validate.value == true) 0 else 1

    init {
        auth.value = Auth()
    }
}
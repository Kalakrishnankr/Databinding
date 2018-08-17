package com.beachpartnerllc.beachpartner.user.auth

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.user.Gender
import com.beachpartnerllc.beachpartner.user.Profile
import com.beachpartnerllc.beachpartner.user.UserType
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Jun 2018 at 2:44 PM
 */
class AuthViewModel @Inject constructor(private val mRepo: AuthRepository, private val app: Application) : ViewModel() {
    val auth = MutableLiveData<Auth>()
    val profile = MutableLiveData<Profile>()
    val state = MutableLiveData<AuthState>()
    val selectedStatePosition = MutableLiveData<Int>()

    val signInValidate = MutableLiveData<Boolean>()
    val signUpValidate = MutableLiveData<Boolean>()
    val signUp2Validate = MutableLiveData<Boolean>()

    fun onSignIn() = mRepo.signIn(auth.value!!, state)

    fun signInSkipInitCount(): Long = if (signInValidate.value == true) 0 else 1

    fun signUpSkipInitCount(): Long = if (signUpValidate.value == true) 0 else 1

    fun signUp2SkipInitCount(): Long = if (signUp2Validate.value == true) 0 else 1

    fun signUp() = mRepo.register(profile.value!!, state)

    fun setState(position: Int) {
        val user = profile.value!!
        user.state = app.resources.getStringArray(R.array.states)[position]
        profile.value = user
    }

    init {
        auth.value = Auth()
        profile.value = Profile("Sam", "Rob", "Alabama", Gender.MALE, UserType.ATHLETE,
            "sam@test.com", "9663379596", "samzionbhavan#158", "05/14/1994")
    }
}
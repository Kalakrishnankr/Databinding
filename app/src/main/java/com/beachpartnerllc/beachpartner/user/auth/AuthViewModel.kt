package com.beachpartnerllc.beachpartner.user.auth

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.*
import androidx.lifecycle.ViewModel
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.etc.base.BaseErrorEvent
import com.beachpartnerllc.beachpartner.etc.model.rest.RequestState
import com.beachpartnerllc.beachpartner.user.Profile
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Jun 2018 at 2:44 PM
 */
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val app: Application) : ViewModel() {
    
    val loading = MutableLiveData<Boolean>()
    val auth = MutableLiveData<Auth>()
    val profile = MutableLiveData<Profile>()
    
    val nameError = MutableLiveData<BaseErrorEvent>()
    
    val state = MutableLiveData<AuthState>()
    val selectedStatePosition = MutableLiveData<Int>()

    val signInValidate = MutableLiveData<Boolean>()
    val signUpValidate = MutableLiveData<Boolean>()
    val signUp2Validate = MutableLiveData<Boolean>()
    
    fun onSignIn() = repo.signIn(auth.value!!, state)

    fun signInSkipInitCount(): Long = if (signInValidate.value == true) 0 else 1

    fun signUpSkipInitCount(): Long = if (signUpValidate.value == true) 0 else 1

    fun signUp2SkipInitCount(): Long = if (signUp2Validate.value == true) 0 else 1
    
    fun signUp() = repo.register(profile.value!!, state)

    fun setState(position: Int) {
        val user = profile.value!!
        user.state = app.resources.getStringArray(R.array.states)[position]
        profile.value = user
    }
    
    fun getStates() = map(repo.getStateList()) {
        loading.value = it.status == RequestState.LOADING
        it
    }!!

    init {
        auth.value = Auth()
        profile.value = Profile()
    }
}
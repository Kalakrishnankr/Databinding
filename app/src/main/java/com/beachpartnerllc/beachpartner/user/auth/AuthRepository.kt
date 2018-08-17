package com.beachpartnerllc.beachpartner.user.auth

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.beachpartnerllc.beachpartner.etc.common.Repository
import com.beachpartnerllc.beachpartner.etc.model.ApiService
import com.beachpartnerllc.beachpartner.user.Profile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Jun 2018 at 2:47 PM
 */
@Singleton
class AuthRepository @Inject constructor(private val mApi: ApiService, app: Application) : Repository(app) {
    fun signIn(auth: Auth, state: MutableLiveData<AuthState>) {
        state.value = AuthState.LOADING

        mApi.signIn(auth).enqueue(object : Callback<Auth?> {
            override fun onFailure(call: Call<Auth?>?, t: Throwable?) {
                httpRequestFailed(call, t)
                state.value = AuthState.REQUEST_FAILED
            }

            override fun onResponse(call: Call<Auth?>?, response: Response<Auth?>?) {
                state.value = AuthState.AUTHENTICATED
            }
        })
    }

    fun register(profile: Profile, state: MutableLiveData<AuthState>) {
        state.value = AuthState.LOADING

        mApi.register(profile).enqueue(object : Callback<Any?> {
            override fun onFailure(call: Call<Any?>?, t: Throwable?) {
                httpRequestFailed(call, t)
                state.value = AuthState.REQUEST_FAILED
            }

            override fun onResponse(call: Call<Any?>?, response: Response<Any?>?) {
            }
        })
    }
}
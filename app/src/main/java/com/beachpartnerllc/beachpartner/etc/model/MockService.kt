package com.beachpartnerllc.beachpartner.etc.model

import com.beachpartnerllc.beachpartner.user.auth.Auth
import com.beachpartnerllc.beachpartner.user.auth.Profile
import retrofit2.Call
import retrofit2.mock.BehaviorDelegate

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 05 Dec 2017 at 7:07 PM
 */
class MockService(private val delegate: BehaviorDelegate<ApiService>) : ApiService {
    override fun signIn(auth: Auth): Call<Auth> {
        return delegate.returningResponse(auth).signIn(auth)
    }

    override fun register(profile: Profile): Call<Any> {
        TODO("Mock API not implemented")
    }
}
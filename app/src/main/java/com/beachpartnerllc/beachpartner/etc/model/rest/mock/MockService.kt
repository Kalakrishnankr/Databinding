package com.beachpartnerllc.beachpartner.etc.model.rest.mock

import androidx.lifecycle.LiveData
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiResponse
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiService
import com.beachpartnerllc.beachpartner.user.Profile
import com.beachpartnerllc.beachpartner.user.auth.Auth
import com.beachpartnerllc.beachpartner.user.state.State
import retrofit2.Call
import retrofit2.mock.BehaviorDelegate

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 05 Dec 2017 at 7:07 PM
 */
class MockService(private val delegate: BehaviorDelegate<ApiService>) : ApiService {
	override fun getStates(): LiveData<ApiResponse<List<State>>> {
		TODO()
	}
    
    override fun signIn(auth: Auth): Call<Auth> {
        return delegate.returningResponse(auth).signIn(auth)
    }

    override fun register(profile: Profile): Call<Any> {
        return delegate.returningResponse(profile).register(profile)
    }
}
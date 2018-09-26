package com.beachpartnerllc.beachpartner.etc.model.rest

import androidx.lifecycle.LiveData
import com.beachpartnerllc.beachpartner.user.Profile
import com.beachpartnerllc.beachpartner.user.Session
import com.beachpartnerllc.beachpartner.user.auth.Auth
import com.beachpartnerllc.beachpartner.user.state.State
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 27 Nov 2017 at 2:48 PM
 */
interface ApiService {
    @Headers(HEADER_NO_AUTH)
    @POST("authenticate")
    fun signIn(@Body auth: Auth): Call<Resource<Session>>

    @Headers(HEADER_NO_AUTH)
    @POST("user/register")
    fun register(@Body profile: Profile): Call<Resource<Any>>
	
	@Headers(HEADER_NO_AUTH)
	@GET("states")
	fun getStates(): LiveData<ApiResponse<List<State>>>
	
	@Headers(HEADER_NO_AUTH)
	@GET("users/search")
	fun getProfiles(): Call<Resource<List<Profile>>>
	
	@Headers(HEADER_NO_AUTH)
	@POST("users/request-friendship/{userId}")
	fun rightSwipe(userId: Int): Call<Resource<Profile>>
	
	@Headers(HEADER_NO_AUTH)
	@POST("users/reject-friendship/{userId}")
	fun leftSwipe(userId: Int) : Call<Resource<Profile>>
	
	@Headers(HEADER_NO_AUTH)
	@POST("users/hifi/{userId}")
	fun topSwipe(userId: Int) : Call<Resource<Profile>>
	
	
	companion object {
	    const val URL_BASE = "http://10.0.2.2:5000/"
        const val NO_AUTH = "No-Auth"
        private const val HEADER_NO_AUTH = "$NO_AUTH: true"
    }
}
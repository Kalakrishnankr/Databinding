package com.beachpartnerllc.beachpartner.etc.model

import com.beachpartnerllc.beachpartner.user.auth.Auth
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 27 Nov 2017 at 2:48 PM
 */
interface ApiService {
    @Headers(HEADER_NO_AUTH)
    @POST("authenticate")
    fun signIn(@Body auth: Auth): Call<Auth>

    companion object {
        const val URL_BASE = "https://beachpartner.com/api/"
        const val NO_AUTH = "No-Auth"
        private const val HEADER_NO_AUTH = "$NO_AUTH: true"
    }
}
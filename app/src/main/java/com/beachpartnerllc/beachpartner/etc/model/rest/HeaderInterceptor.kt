package com.beachpartnerllc.beachpartner.etc.model.rest

import android.app.Application
import android.content.Intent
import com.beachpartnerllc.beachpartner.etc.model.pref.Preference
import com.beachpartnerllc.beachpartner.init.MainActivity
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 30 Jan 2018 at 2:29 PM
 */
class HeaderInterceptor(private val mApp: Application, private val mPref: Preference) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request()!!

        request = request.newBuilder()
            .addHeader(API_KEY, API_VALUE)
            .build()

        request = if (request.header(ApiService.NO_AUTH) == null) {
            request.newBuilder()
                .addHeader(TOKEN_KEY, TOKEN_VALUE + mPref.sessionId!!)
                .build()
        } else {
            request.newBuilder()
                .removeHeader(ApiService.NO_AUTH)
                .build()
        }

        val response = chain.proceed(request)

        if (response.code() == 401) {
            mPref.clear()
            val intent = Intent(mApp, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            mApp.startActivity(intent)
        }

        return response
    }

    private companion object {
        const val API_KEY = "x-api-key"
        const val API_VALUE = "LozC7H0rINg8kO1FMrm1RRBIzjw9AozsQwguKB3J"
        const val TOKEN_KEY = "Authorization"
        const val TOKEN_VALUE = "Bearer "
    }
}
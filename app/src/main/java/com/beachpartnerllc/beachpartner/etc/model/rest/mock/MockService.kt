package com.beachpartnerllc.beachpartner.etc.model.rest.mock

import android.app.Application
import androidx.lifecycle.LiveData
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiResponse
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiService
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import com.beachpartnerllc.beachpartner.user.Profile
import com.beachpartnerllc.beachpartner.user.Session
import com.beachpartnerllc.beachpartner.user.auth.Auth
import com.beachpartnerllc.beachpartner.user.state.State
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.Calls
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 05 Dec 2017 at 7:07 PM
 */
class MockService(
        private val delegate: BehaviorDelegate<ApiService>,
        private val serializer: Gson,
        private val app: Application) : ApiService {

    override fun getStates(): LiveData<ApiResponse<List<State>>> {
        val data = stringFromFile("get_state.json")
        val response: List<State> = serializer.fromJson(data, object : TypeToken<List<State>>() {}.type)
        return delegate.returningResponse(response).getStates()
    }

    override fun signIn(auth: Auth): Call<Resource<Session>> {
        val data = stringFromFile("login_200_ok.json")
        val response: Resource<Session> = serializer.fromJson(data, object : TypeToken<Resource<Session>>() {}.type)
        return delegate.returning(Calls.response(response)).signIn(auth)
    }

    override fun register(profile: Profile): Call<Resource<Any>> {
        val response = Response.Builder()
                .code(201)
                .body(ResponseBody.create(MediaType.parse("application/json"), profile.toString()))
        return delegate.returningResponse(response).register(profile)
    }

    private fun stringFromFile(filePath: String): String {
        val stream = app.resources.assets.open(filePath)
        val reader = BufferedReader(InputStreamReader(stream))
        val line = reader.readLines().joinToString(separator = "\n")
        reader.close()
        stream.close()
        return line
    }
}
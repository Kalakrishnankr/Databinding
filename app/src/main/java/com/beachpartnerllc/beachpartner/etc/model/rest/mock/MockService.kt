package com.beachpartnerllc.beachpartner.etc.model.rest.mock

import android.app.Application
import androidx.lifecycle.LiveData
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiResponse
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiService
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import com.beachpartnerllc.beachpartner.event.Event
import com.beachpartnerllc.beachpartner.finder.Flag
import com.beachpartnerllc.beachpartner.finder.Search
import com.beachpartnerllc.beachpartner.user.auth.Auth
import com.beachpartnerllc.beachpartner.user.profile.Profile
import com.beachpartnerllc.beachpartner.user.profile.Session
import com.beachpartnerllc.beachpartner.user.state.State
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.mock.BehaviorDelegate
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 05 Dec 2017 at 7:07 PM
 */
class MockService(
	private val delegate: BehaviorDelegate<ApiService>,
	private val serializer: Gson,
	private val app: Application) : ApiService {

    override fun getStates(): LiveData<ApiResponse<List<State>>> {
        val data = stringFromFile("get_state")
        val response: List<State> = serializer.fromJson(data, object : TypeToken<List<State>>() {}.type)
        return delegate.returningResponse(response).getStates()
    }

    override fun signIn(auth: Auth): Call<Resource<Session>> {
        val filePath = if ((0..1).random() == 0) "login_200_ok" else "login_422_invalid_credentials"
        val data = stringFromFile(filePath)
        val response: Resource<Session> = serializer.fromJson(data, object : TypeToken<Resource<Session>>() {}.type)
        return delegate.returningResponse(response).signIn(auth)
    }

    override fun register(profile: Profile): Call<Resource<Any>> {
        return delegate.returningResponse(Resource.success(profile)).register(profile)
    }

    override fun update(profile: Profile?): Call<Resource<Profile>> {
        return delegate.returningResponse(Resource.success(profile)).update(profile)
    }

    override fun getEvent(date: Date, limit: Int, index: Int): Call<Resource<List<Event>>> {
        val data = stringFromFile("get_events")
        val response: List<Event> = serializer.fromJson(data, object : TypeToken<List<Event>>() {}.type)
        return delegate.returningResponse(Resource.success(response)).getEvent(date, limit, index)
    }

    override fun getEvent(eventId: Int): LiveData<ApiResponse<Event>> {
        val data = stringFromFile("get_event")
        val response: Event = serializer.fromJson(data, object : TypeToken<Event>() {}.type)
        return delegate.returningResponse(response).getEvent(eventId)
    }

    override fun getConnections(): LiveData<ApiResponse<List<Profile>>> {
        val data = stringFromFile("get_connections")
        val response: List<Profile> = serializer.fromJson(data, object : TypeToken<List<Profile>>() {}.type)
        return delegate.returningResponse(response).getConnections()
    }

    override fun sendInvitation(invitees: List<Int>): Call<Resource<Any>> {
        return delegate.returningResponse(Resource.success(null)).sendInvitation(invitees)
    }

    override fun getStripProfile(): Call<Resource<List<Profile>>> {
        val data = stringFromFile("get_profiles")
        val response: List<Profile> = serializer.fromJson(data, object : TypeToken<List<Profile>>() {}.type)
        return delegate.returningResponse(Resource.success(response)).getStripProfile()
    }

    override fun getProfile(userId: Int): Call<Resource<Profile>> {
        val data = stringFromFile("get_profile")
        val response: Profile = serializer.fromJson(data, object : TypeToken<Profile>() {}.type)
        return delegate.returningResponse(Resource.success(response)).getProfile(userId)
    }

    override fun blockUser(request: HashMap<String, Any>): Call<Flag> {
        return delegate.returningResponse("success").blockUser(request)
    }

    override fun searchProfile(search: Search): Call<Resource<List<Profile>>> {
        val data = stringFromFile("get_profiles")
        val response: List<Profile> = serializer.fromJson(data, object : TypeToken<List<Profile>>() {}.type)
        return delegate.returningResponse(Resource.success(response)).searchProfile(search)
    }

    override fun dislikeUser(userId: Int): Call<Resource<Profile>> {
        return delegate.returningResponse("success").dislikeUser(userId)
    }

    override fun likeUser(userId: Int): Call<Resource<Profile>> {
        return delegate.returningResponse("success").likeUser(userId)
    }

    override fun hiFiveUser(userId: Int): Call<Resource<Profile>> {
        return delegate.returningResponse("success").likeUser(userId)
    }

    private fun stringFromFile(filePath: String): String {
        val stream = app.resources.assets.open("$filePath.json")
        val reader = BufferedReader(InputStreamReader(stream))
        val line = reader.readLines().joinToString(separator = "\n")
        reader.close()
        stream.close()
        return line
    }
}

fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) + start
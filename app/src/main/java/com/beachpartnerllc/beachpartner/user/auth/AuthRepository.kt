package com.beachpartnerllc.beachpartner.user.auth

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beachpartnerllc.beachpartner.etc.base.Repository
import com.beachpartnerllc.beachpartner.etc.exec.AppExecutors
import com.beachpartnerllc.beachpartner.etc.model.db.AppDatabase
import com.beachpartnerllc.beachpartner.etc.model.pref.Preference
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiService
import com.beachpartnerllc.beachpartner.etc.model.rest.NetworkBoundResource
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import com.beachpartnerllc.beachpartner.user.Profile
import com.beachpartnerllc.beachpartner.user.Session
import com.beachpartnerllc.beachpartner.user.state.State
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
class AuthRepository @Inject constructor(
        private val api: ApiService,
        private val db: AppDatabase,
        private val pref: Preference,
        private val exec: AppExecutors,
        app: Application) : Repository(app) {

    fun getStateList() = object : NetworkBoundResource<List<State>, List<State>>(exec) {
        override fun saveCallResult(item: List<State>) = db.state().insertStates(item)

        override fun shouldFetch(data: List<State>?) = data == null || data.isEmpty()

        override fun loadFromDb() = db.state().getStates()

        override fun createCall() = api.getStates()

        override fun onFetchFailed() {
            createCall()
        }
    }.asLiveData()

    fun register(profile: Profile): LiveData<Resource<Profile>> {
        val state = MutableLiveData<Resource<Profile>>()
        state.value = Resource.loading()
        api.register(profile).enqueue(object : Callback<Resource<Any>?> {
            override fun onFailure(call: Call<Resource<Any>?>, t: Throwable) = httpRequestFailed(call, t, state)

            override fun onResponse(call: Call<Resource<Any>?>, response: Response<Resource<Any>?>) {
                if (response.isSuccessful) {
                    state.value = Resource.success(profile)
                } else {
                    state.value = Resource.error(response)
                }
            }
        })

        return state
    }

    fun signIn(auth: Auth): LiveData<Resource<Session>> {
        val state = MutableLiveData<Resource<Session>>()
        state.value = Resource.loading()
        api.signIn(auth).enqueue(object : Callback<Resource<Session>?> {
            override fun onFailure(call: Call<Resource<Session>?>, t: Throwable) = httpRequestFailed(call, t, state)

            override fun onResponse(call: Call<Resource<Session>?>, response: Response<Resource<Session>?>) {
                if (response.isSuccessful) {
                    val body = response.body()!!
                    if (body.code == HTTP_OK) pref.setSession(body.data!!)
                    state.value = body
                } else {
                    state.value = Resource.error(response)
                }
            }
        })

        return state
    }
}
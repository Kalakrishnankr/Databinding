package com.beachpartnerllc.beachpartner.user.auth

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beachpartnerllc.beachpartner.etc.base.BaseRepository
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
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Jun 2018 at 2:47 PM
 */
@Singleton
class AuthRepository @Inject constructor(
        private val api: ApiService,
        private val retrofit: Retrofit,
        private val db: AppDatabase,
        private val pref: Preference,
        private val exec: AppExecutors,
        app: Application) : BaseRepository(app) {

    fun getStateList() = object : NetworkBoundResource<List<State>, List<State>>(exec) {
        override fun saveCallResult(item: List<State>) = db.stateDao().insertStates(item)

        override fun shouldFetch(data: List<State>?) = data == null || data.isEmpty()

        override fun loadFromDb() = db.stateDao().getStates()

        override fun createCall() = api.getStates()
    }.asLiveData()

    fun register(profile: Profile): LiveData<Resource<Profile>> {
        val state = MutableLiveData<Resource<Profile>>()
        state.value = Resource.loading()
        api.register(profile).enqueue(object : Callback<Resource<Any>?> {
            override fun onFailure(call: Call<Resource<Any>?>, t: Throwable) {
                httpRequestFailed(call, t)
                state.value = Resource.error()
            }

            override fun onResponse(call: Call<Resource<Any>?>, response: Response<Resource<Any>?>) {
                if (response.isSuccessful) {
                    state.value = Resource.success(profile)
                }
            }
        })

        return state
    }

    fun signIn(auth: Auth): LiveData<Resource<Profile>> {
        val state = MutableLiveData<Resource<Profile>>()
        state.value = Resource.loading()
        api.signIn(auth).enqueue(object : Callback<Resource<Session>?> {
            override fun onFailure(call: Call<Resource<Session>?>, t: Throwable) {
                httpRequestFailed(call, t)
                state.value = Resource.error()
            }

            override fun onResponse(call: Call<Resource<Session>?>, response: Response<Resource<Session>?>) {
                when (response.code()) {
                    HTTP_OK -> {
                        pref.setSession(response.body()!!.data!!)
                        state.value = Resource.success(response.body()!!.data!!.profile)
                    }

                    else -> state.value = Resource.error()
                }
            }
        })

        return state
    }
}
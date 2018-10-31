package com.beachpartnerllc.beachpartner.connection

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beachpartnerllc.beachpartner.etc.base.Repository
import com.beachpartnerllc.beachpartner.etc.exec.AppExecutors
import com.beachpartnerllc.beachpartner.etc.model.db.AppDatabase
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiService
import com.beachpartnerllc.beachpartner.etc.model.rest.NetworkBoundResource
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import com.beachpartnerllc.beachpartner.user.profile.Profile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionRepository
@Inject constructor(
    private val db: AppDatabase,
    private val api: ApiService,
    private val exec: AppExecutors,
    app: Application
) : Repository(app) {

    fun getConnections() = object : NetworkBoundResource<List<Profile>, List<Profile>>(exec) {
        override fun saveCallResult(item: List<Profile>) = db.connection().insert(item)

        override fun shouldFetch(data: List<Profile>?) = data == null || data.isEmpty()

        override fun loadFromDb(): LiveData<List<Profile>> = db.connection().getConnections()

        override fun createCall() = api.getConnections()
    }.asLiveData()

    fun sendInvitation(invitees: List<Int>): LiveData<Resource<Any>> {
        val callback = MutableLiveData<Resource<Any>>()
        callback.value = Resource.success()
        api.sendInvitation(invitees).enqueue(object : Callback<Resource<Any>?> {
            override fun onFailure(call: Call<Resource<Any>?>, t: Throwable) {
                httpRequestFailed(call, t, callback)
            }

            override fun onResponse(
                call: Call<Resource<Any>?>,
                response: Response<Resource<Any>?>
            ) {
                if (response.isSuccessful) {
                    callback.value = response.body()
                } else {
                    callback.value = Resource.error(response)
                }
            }
        })

        return callback
    }
}

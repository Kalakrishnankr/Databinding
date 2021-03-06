package com.beachpartnerllc.beachpartner.user.auth

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.etc.base.Repository
import com.beachpartnerllc.beachpartner.etc.exec.AppExecutors
import com.beachpartnerllc.beachpartner.etc.model.aws.AwsServiceModule.Companion.URL_BUCKET
import com.beachpartnerllc.beachpartner.etc.model.db.AppDatabase
import com.beachpartnerllc.beachpartner.etc.model.pref.Preference
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiService
import com.beachpartnerllc.beachpartner.etc.model.rest.NetworkBoundResource
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import com.beachpartnerllc.beachpartner.user.profile.Profile
import com.beachpartnerllc.beachpartner.user.profile.Session
import com.beachpartnerllc.beachpartner.user.state.State
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.File
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
    private val transfer: TransferUtility,
    app: Application
) : Repository(app) {

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
            override fun onFailure(call: Call<Resource<Any>?>, t: Throwable) =
                httpRequestFailed(call, t, state)

            override fun onResponse(
                call: Call<Resource<Any>?>,
                response: Response<Resource<Any>?>
            ) {
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
            override fun onFailure(call: Call<Resource<Session>?>, t: Throwable) =
                httpRequestFailed(call, t, state)

            override fun onResponse(
                call: Call<Resource<Session>?>,
                response: Response<Resource<Session>?>
            ) {
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

    fun update(profile: Profile?): LiveData<Resource<Profile>> {
        val state = MutableLiveData<Resource<Profile>>()
        state.value = Resource.loading()
        api.update(profile).enqueue(object : Callback<Resource<Profile>?> {
            override fun onFailure(call: Call<Resource<Profile>?>, t: Throwable) {
                state.value = Resource.error()
            }

            override fun onResponse(
                call: Call<Resource<Profile>?>,
                response: Response<Resource<Profile>?>
            ) {
                when (response.code()) {
                    HTTP_OK -> {
                        state.value = Resource.success(profile)
                    }
                }
            }
        })

        return state
    }

    fun uploadFileToS3(path: String, extension: String): LiveData<Resource<String>> {
        val state = MutableLiveData<Resource<String>>()
        val file = File(path)
        val key = "avatar/${System.nanoTime()}$extension"
        transfer.upload(key, file, CannedAccessControlList.PublicRead)
            .setTransferListener(object : TransferListener {
                override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                    val percentDoneF = bytesCurrent.toFloat() / bytesTotal.toFloat() * 100
                    Timber.d("ID:$id bytesCurrent: $bytesCurrent bytesTotal: $bytesTotal $percentDoneF")
                    state.value = Resource.loading(code = percentDoneF.toInt())
                }

                override fun onStateChanged(id: Int, transferState: TransferState?) {
                    Timber.d("State : %s", transferState?.toString())
                    if (TransferState.COMPLETED == transferState) {
                        val url = URL_BUCKET + key
                        Timber.d(url)
                        state.value = Resource.success(url)
                    }
                }

                override fun onError(id: Int, ex: Exception?) {
                    state.value = Resource.error()
                    Timber.e(ex)
                    Toast.makeText(app, R.string.upload_error, Toast.LENGTH_LONG).show()
                }
            })
        return state
    }
}
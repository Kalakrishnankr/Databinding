package com.beachpartnerllc.beachpartner.etc.common

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.StringRes
import android.widget.Toast
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.etc.model.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Jun 2018 at 2:49 PM
 */
abstract class Repository(private val app: Application) {
    fun loading(callback: MutableLiveData<Resource<Any>>) {
        callback.value = Resource.loading()
    }

    fun <T> httpRequestFailed(call: Call<T>?, t: Throwable?) {
        Timber.e(t)
        if (!call!!.isExecuted) shortToast(R.string.no_internet_connection)
    }


    fun longToast(@StringRes res: Int) {
        Toast.makeText(app, res, Toast.LENGTH_LONG).show()
    }

    fun longToast(msg: String) {
        Toast.makeText(app, msg, Toast.LENGTH_LONG).show()
    }

    fun shortToast(@StringRes res: Int) {
        Toast.makeText(app, res, Toast.LENGTH_SHORT).show()
    }

    fun shortToast(msg: String) {
        Toast.makeText(app, msg, Toast.LENGTH_SHORT).show()
    }

    inner class ApiCallback<T>(private val callback: MutableLiveData<Resource<T>>) : Callback<T> {
        override fun onFailure(call: Call<T>?, t: Throwable?) {
            shortToast(R.string.no_internet_connection)
            callback.value = Resource.error()
        }

        override fun onResponse(call: Call<T>?, response: Response<T>) {
            callback.value = Resource.success(response.body()!!)
        }
    }

    companion object {
        const val HTTP_OK = 200
        const val HTTP_CREATED = 201
    }
}
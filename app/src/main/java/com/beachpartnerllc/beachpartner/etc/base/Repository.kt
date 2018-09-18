package com.beachpartnerllc.beachpartner.etc.base

import android.app.Application
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import retrofit2.Call
import timber.log.Timber

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 09 Jun 2018 at 11:06 AM
 */
abstract class Repository(protected val app: Application) {
	fun <T> loading(callback: MutableLiveData<Resource<T>>) {
		callback.value = Resource.loading()
	}

	fun <C, T> httpRequestFailed(call: Call<C>?, t: Throwable?, callback: MutableLiveData<Resource<T>>) {
		Timber.e(t)
		callback.value = Resource.error(message = app.getString(R.string.no_internet_connection))
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

	/*inner class ApiCallback<T>(private val callback: MutableLiveData<Resource<T>>) : Callback<T> {
		override fun onFailure(call: Call<T>?, t: Throwable?) {
			httpRequestFailed(call, t)
		}
		
		override fun onResponse(call: Call<T>?, response: Response<T>) {
			if (!response.isSuccessful) {
				longToast(R.string.oops_something_went_wrong)
				callback.value = Resource.error(response.toString())
				return
			}
			
			when (response.code()) {
				HTTP_OK -> callback.value = Resource.success(response.body())
			}
		}
	}*/
	
	companion object {
		const val HTTP_OK = 200
		const val HTTP_CREATED = 201
		const val HTTP_INVALID_INPUT = 422
	}
}
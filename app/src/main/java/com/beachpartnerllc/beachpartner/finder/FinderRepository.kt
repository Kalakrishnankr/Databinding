package com.beachpartnerllc.beachpartner.finder

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.beachpartnerllc.beachpartner.etc.base.Repository
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiService
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import com.beachpartnerllc.beachpartner.user.Profile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 17 Sep 2018 at 4:15 PM
 */
@Singleton
class FinderRepository @Inject constructor(
		private val api: ApiService,
		app: Application) : Repository(app) {

	fun searchProfile(search: Search): MutableLiveData<Resource<List<Profile>>> {
		val state = MutableLiveData<Resource<List<Profile>>>()
		state.value = Resource.loading()
		api.searchProfile(search).enqueue(object :
				Callback<Resource<List<Profile>>?> {
			override fun onFailure(call: Call<Resource<List<Profile>>?>, t: Throwable) {
				httpRequestFailed(call, t, state)
			}

			override fun onResponse(call: Call<Resource<List<Profile>>?>, response: Response<Resource<List<Profile>>?>) {
				if (response.isSuccessful) {
					state.value = response.body()
				} else {
					state.value = Resource.error(response)
				}
			}

		})
		return state
	}

	fun likeUser(profile: Profile): MutableLiveData<Resource<Profile>> {
		val state = MutableLiveData<Resource<Profile>>()
		state.value = Resource.loading()
		api.likeUser(profile.userId).enqueue(object : Callback<Resource<Profile>?> {
			override fun onFailure(call: Call<Resource<Profile>?>, t: Throwable) {
				httpRequestFailed(call, t, state)
			}

			override fun onResponse(call: Call<Resource<Profile>?>, response: Response<Resource<Profile>?>) {
				if (response.isSuccessful) {
					state.value = response.body()
				} else {
					state.value = Resource.error(response)
				}
			}
		})
		return state
	}

	fun dislikeUser(profile: Profile): MutableLiveData<Resource<Profile>> {
		val state = MutableLiveData<Resource<Profile>>()
		state.value = Resource.loading()
		api.dislikeUser(profile.userId).enqueue(object : Callback<Resource<Profile>?> {
			override fun onFailure(call: Call<Resource<Profile>?>, t: Throwable) {
				httpRequestFailed(call, t, state)
			}

			override fun onResponse(call: Call<Resource<Profile>?>, response: Response<Resource<Profile>?>) {
				if (response.isSuccessful) {
					state.value = response.body()
				} else {
					state.value = Resource.error(response)
				}
			}
		})
		return state
	}

	fun highFiveUser(profile: Profile): MutableLiveData<Resource<Profile>> {
		val state = MutableLiveData<Resource<Profile>>()
		state.value = Resource.loading()
		api.hiFiveUser(profile.userId).enqueue(object : Callback<Resource<Profile>?> {
			override fun onFailure(call: Call<Resource<Profile>?>, t: Throwable) {
				httpRequestFailed(call, t, state)
			}

			override fun onResponse(call: Call<Resource<Profile>?>, response: Response<Resource<Profile>?>) {
				if (response.isSuccessful) {
					state.value = response.body()
				} else {
					state.value = Resource.error(response)
				}
			}
		})

		return state
	}

	fun blockUser(profile: Profile): MutableLiveData<Resource<Flag>> {
		val flag = MutableLiveData<Resource<Flag>>()
		flag.value = Resource.loading()
		val request = hashMapOf(
				"flagUserId" to profile.userId,
				"flagReason" to "Unknown"
		)
		api.blockUser(request).enqueue(object : Callback<Flag?> {
			override fun onFailure(call: Call<Flag?>, t: Throwable) {
				httpRequestFailed(call, t, flag)
			}

			override fun onResponse(call: Call<Flag?>, response: Response<Flag?>) {
				flag.value = if (response.code() == 200) Resource.success(response.body()) else Resource.error()
			}
		})
		return flag
	}

	fun getProfile(userId: Int): MutableLiveData<Resource<Profile>> {
		val state = MutableLiveData<Resource<Profile>>()
		state.value = Resource.loading()
		api.getProfile(userId).enqueue(object : Callback<Resource<Profile>?> {
			override fun onFailure(call: Call<Resource<Profile>?>, t: Throwable) {
				httpRequestFailed(call, t, state)
			}

			override fun onResponse(call: Call<Resource<Profile>?>, response: Response<Resource<Profile>?>) {
				if (response.isSuccessful) {
					state.value = response.body()
				} else {
					state.value = Resource.error(response)
				}
			}
		})
		return state
	}

	fun getStripProfile(): MutableLiveData<Resource<List<Profile>>> {
		val state = MutableLiveData<Resource<List<Profile>>>()
		state.value = Resource.loading()
		api.getStripProfile().enqueue(object : Callback<Resource<List<Profile>>?> {
			override fun onFailure(call: Call<Resource<List<Profile>>?>, t: Throwable) {
				httpRequestFailed(call, t, state)
			}

			override fun onResponse(call: Call<Resource<List<Profile>>?>, response: Response<Resource<List<Profile>>?>) {
				if (response.isSuccessful) {
					state.value = response.body()
				} else {
					state.value = Resource.error(response)
				}
			}
		})
		return state
	}
}

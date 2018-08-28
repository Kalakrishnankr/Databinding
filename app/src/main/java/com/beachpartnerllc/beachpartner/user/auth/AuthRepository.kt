package com.beachpartnerllc.beachpartner.user.auth

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beachpartnerllc.beachpartner.etc.base.BaseRepository
import com.beachpartnerllc.beachpartner.etc.exec.AppExecutors
import com.beachpartnerllc.beachpartner.etc.model.db.AppDatabase
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiResponse
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiService
import com.beachpartnerllc.beachpartner.etc.model.rest.NetworkBoundResource
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import com.beachpartnerllc.beachpartner.etc.util.RateLimiter
import com.beachpartnerllc.beachpartner.user.Profile
import com.beachpartnerllc.beachpartner.user.state.State
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit
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
	private val exec: AppExecutors,
	app: Application) : BaseRepository(app) {
	
	private val invalidateTimer = RateLimiter<String>(30, TimeUnit.SECONDS)
	
	fun signIn(auth: Auth, state: MutableLiveData<AuthState>) {
        state.value = AuthState.LOADING
		
		api.signIn(auth).enqueue(object : Callback<Auth?> {
            override fun onFailure(call: Call<Auth?>?, t: Throwable?) {
                httpRequestFailed(call, t)
                state.value = AuthState.REQUEST_FAILED
            }

            override fun onResponse(call: Call<Auth?>?, response: Response<Auth?>?) {
                state.value = AuthState.AUTHENTICATED
            }
        })
    }
	
	fun register(profile: Profile): LiveData<Resource<Profile>> {
		val state = MutableLiveData<Resource<Profile>>()
		state.value = Resource.loading()
		
		api.register(profile).enqueue(object : Callback<Resource<Profile>?> {
			override fun onFailure(call: Call<Resource<Profile>?>, t: Throwable) {
				httpRequestFailed(call, t)
				state.value = Resource.error()
			}
			
			override fun onResponse(call: Call<Resource<Profile>?>, response: Response<Resource<Profile>?>) {
				state.value = response.body()
			}
        })
		
		return state
    }
	
	
	fun getStateList(): LiveData<Resource<List<State>>> {
		return object : NetworkBoundResource<List<State>, List<State>>(exec) {
			override fun saveCallResult(item: List<State>) {
				db.stateDao().insertStates(item)
			}
			
			override fun shouldFetch(data: List<State>?): Boolean {
				return data == null || data.isEmpty() || invalidateTimer.shouldFetch(State::class.java.name)
			}
			
			override fun loadFromDb(): LiveData<List<State>> {
				return db.stateDao().getAllStates()
			}
			
			override fun createCall(): LiveData<ApiResponse<List<State>>> {
				return api.getStates()
			}
		}.asLiveData()
	}
}
package com.beachpartnerllc.beachpartner.finder

import android.app.Application
import com.beachpartnerllc.beachpartner.etc.base.BaseRepository
import com.beachpartnerllc.beachpartner.etc.common.RateLimiter
import com.beachpartnerllc.beachpartner.etc.exec.AppExecutors
import com.beachpartnerllc.beachpartner.etc.model.db.AppDatabase
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiService
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 17 Sep 2018 at 4:15 PM
 */
@Singleton
class FinderRepository @Inject constructor(
	private val api: ApiService,
	private val db: AppDatabase,
	private val exec: AppExecutors,
	app: Application) : BaseRepository(app) {
	private val invalidateTimer = RateLimiter<String>(30, TimeUnit.SECONDS)
	
	/*fun getStateList(): LiveData<Resource<List<State>>> {
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
	}*/
	
	/*fun play(search: Search): LiveData<Resource<Search>>{
		val state = MutableLiveData<Resource<Search>>()
		state.value = Resource.loading()
		
		api.getProfiles(search).enqueue(object : Callback<Resource<Search>?>{
			override fun onFailure(call: Call<Resource<Search>?>, t: Throwable) {
				httpRequestFailed(call,t)
				state.value = Resource.error()
			}
			
			override fun onResponse(call: Call<Resource<Search>?>, response: Response<Resource<Search>?>) {
			state.value = response.body()
			}
		})
		return state
	}*/
}


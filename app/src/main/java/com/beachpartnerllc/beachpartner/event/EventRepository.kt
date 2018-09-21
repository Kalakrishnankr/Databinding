package com.beachpartnerllc.beachpartner.event

import android.app.Application
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.*
import androidx.paging.LivePagedListBuilder
import com.beachpartnerllc.beachpartner.etc.base.Repository
import com.beachpartnerllc.beachpartner.etc.exec.AppExecutors
import com.beachpartnerllc.beachpartner.etc.model.db.AppDatabase
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiService
import com.beachpartnerllc.beachpartner.etc.model.rest.Listing
import com.beachpartnerllc.beachpartner.etc.model.rest.NetworkBoundResource
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
	private val api: ApiService,
	private val db: AppDatabase,
	private val exec: AppExecutors,
	app: Application) : Repository(app) {
	private var networkPageSize: Int = DEFAULT_NETWORK_PAGE_SIZE
	
	@MainThread
	fun eventsOf(eventDate: Date, pageSize: Int): Listing<Event> {
		// create a boundary callback which will observe when the user reaches to the edges of
		// the list and update the database with extra data.
		val boundaryCallback = EventBoundaryCallback(
			api = api,
			eventDate = eventDate,
			handleResponse = this::insertResultIntoDb,
			ioExecutor = exec.diskIO,
			networkPageSize = networkPageSize)
		// create a data source factory from Room
		val dataSourceFactory = db.event().eventsByDate(eventDate)
		val builder = LivePagedListBuilder(dataSourceFactory, pageSize)
			.setBoundaryCallback(boundaryCallback)
		
		// we are using a mutable live data to trigger refresh requests which eventually calls
		// refresh method and gets a new live data. Each refresh request by the user becomes a newly
		// dispatched data in refreshTrigger
		val refreshTrigger = MutableLiveData<Unit>()
		val refreshState = switchMap(refreshTrigger) {
			refresh(eventDate)
		}
		
		return Listing(
			pagedList = builder.build(),
			networkState = boundaryCallback.networkState,
			retry = {
				boundaryCallback.helper.retryAllFailed()
			},
			refresh = {
				refreshTrigger.value = null
			},
			refreshState = refreshState
		)
	}
	
	
	/**
	 * Inserts the response into the database while also assigning position indices to items.
	 */
	private fun insertResultIntoDb(eventDate: Date, body: Resource<List<Event>>?) {
		body?.data?.let {
			db.runInTransaction {
				db.event().insert(it)
			}
		}
	}
	
	@MainThread
	private fun refresh(eventDate: Date): LiveData<Resource<List<Event>>> {
		val networkState = MutableLiveData<Resource<List<Event>>>()
		networkState.value = Resource.loading()
		api.getEvent(eventDate, networkPageSize).enqueue(
			object : Callback<Resource<List<Event>>> {
				override fun onFailure(call: Call<Resource<List<Event>>>, t: Throwable) {
					// retrofit calls this on main thread so safe to call set value
					httpRequestFailed(call, t, networkState)
				}
				
				override fun onResponse(
					call: Call<Resource<List<Event>>>,
					response: Response<Resource<List<Event>>>) {
					exec.diskIO.execute {
						db.runInTransaction {
							db.event().deleteByDate(eventDate)
							insertResultIntoDb(eventDate, response.body())
						}
						// since we are in bg thread now, post the result.
						networkState.postValue(Resource.success())
					}
				}
			}
		)
		return networkState
	}
	
	fun getEvent(eventId: Int) = object : NetworkBoundResource<Event, Event>(exec) {
		override fun saveCallResult(item: Event) = db.event().insert(item)
		
		override fun shouldFetch(data: Event?) = data == null
		
		override fun loadFromDb() = db.event().getEvent(eventId)
		
		override fun createCall() = api.getEvent(eventId)
		
		override fun onFetchFailed() {
			createCall()
		}
	}.asLiveData()
	
	companion object {
		private const val DEFAULT_NETWORK_PAGE_SIZE = 30
	}
}

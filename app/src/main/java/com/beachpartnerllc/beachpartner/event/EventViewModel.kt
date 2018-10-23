package com.beachpartnerllc.beachpartner.event

import android.text.format.DateFormat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.*
import androidx.lifecycle.ViewModel
import com.beachpartnerllc.beachpartner.etc.common.truncateTime
import com.beachpartnerllc.beachpartner.etc.common.zip
import com.beachpartnerllc.beachpartner.etc.model.rest.RequestState
import com.beachpartnerllc.beachpartner.etc.model.rest.isLoading
import java.util.*
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 18 Sep 2018 at 12:31 PM
 */
class EventViewModel @Inject constructor(private val repo: EventRepository) : ViewModel() {
	val eventDate = MutableLiveData<Date>()
	private val repoResult = map(eventDate) { repo.eventsOf(it, 30) }
	val events = switchMap(repoResult) { it.pagedList }!!
	val networkState = switchMap(repoResult) { it.networkState }!!
	val refreshState = switchMap(repoResult) { it.refreshState }!!
	val isLoading = map(zip(networkState, refreshState)) { it.status == RequestState.LOADING }!!
	val selectedMonth = map(eventDate) { DateFormat.format("MMMM, yyyy", it).toString() }!!
	
	val eventId = MutableLiveData<Int>()
	val eventRes = switchMap(eventId) { repo.getEvent(it) }
	val event = map(eventRes) { it.data }
	val isEventLoading = map(eventRes) { it.isLoading() }!!
	
	fun showEventsOf(date: Date): Boolean {
		if (eventDate.value == date) {
			return false
		}
		eventDate.value = date
		return true
	}
	
	fun retry() {
		val listing = repoResult?.value
		listing?.retry?.invoke()
	}
	
	fun refresh() = repoResult.value?.refresh?.invoke()

	init {
		eventDate.value = Calendar.getInstance().time.truncateTime()
	}
}
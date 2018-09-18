package com.beachpartnerllc.beachpartner.event

import android.text.format.DateFormat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.*
import androidx.lifecycle.ViewModel
import com.beachpartnerllc.beachpartner.etc.common.combine
import com.beachpartnerllc.beachpartner.etc.model.rest.RequestState
import java.util.*
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 18 Sep 2018 at 12:31 PM
 */
class EventViewModel @Inject constructor(private val repo: EventRepository) : ViewModel() {
    private val eventDate = MutableLiveData<Date>()
    private val repoResult = map(eventDate) { repo.eventsOf(it, 30) }
    val events = switchMap(repoResult) { it.pagedList }!!
    val networkState = switchMap(repoResult) { it.networkState }!!
    val refreshState = switchMap(repoResult) { it.refreshState }!!
    val isLoading = map(combine(networkState, refreshState)) { it.status == RequestState.LOADING }!!
    val selectedMonth = map(eventDate) { DateFormat.format("MMMM, yyyy", it).toString() }!!

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

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }
}
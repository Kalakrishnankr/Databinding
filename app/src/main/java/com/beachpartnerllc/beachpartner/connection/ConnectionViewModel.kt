package com.beachpartnerllc.beachpartner.connection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.*
import androidx.lifecycle.ViewModel
import com.beachpartnerllc.beachpartner.etc.model.rest.isLoading
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 25 Sep 2018 at 11:14 AM
 */
class ConnectionViewModel @Inject constructor(
	private val repo: ConnectionRepository) : ViewModel() {
	val loading = MutableLiveData<Boolean>()
	
	fun getConnections() = map(repo.getConnections()) {
		loading.value = it.isLoading()
		it
	}
	
	fun invite(invitees: List<Int>) = map(repo.sendInvitation(invitees)) {
		loading.value = it.isLoading()
		it
	}
}
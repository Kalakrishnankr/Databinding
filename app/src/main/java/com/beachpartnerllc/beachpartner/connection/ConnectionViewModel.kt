package com.beachpartnerllc.beachpartner.connection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.*
import androidx.lifecycle.ViewModel
import com.beachpartnerllc.beachpartner.etc.common.mutableLiveDataOf
import com.beachpartnerllc.beachpartner.etc.model.rest.isLoading
import com.beachpartnerllc.beachpartner.user.profile.Profile
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 25 Sep 2018 at 11:14 AM
 */
class ConnectionViewModel @Inject constructor(
    private val repo: ConnectionRepository) : ViewModel() {

    private val refresh = MutableLiveData<Boolean>()
    val connections = switchMap(refresh) { repo.getConnections() }!!
    val isConnectionLoading = switchMap(connections) { mutableLiveDataOf(it.isLoading()) }!!
    fun refresh() {
        refresh.value = true
    }

    private val invitees = MutableLiveData<List<Int>>()
    val inviteResponse = switchMap(invitees) { repo.sendInvitation(it) }!!
    val isInviteLoading = switchMap(inviteResponse) { mutableLiveDataOf(it.isLoading()) }!!
    fun invite(users: List<Profile>) {
        invitees.value = users.map { it.userId }
    }

    init {
        refresh.value = true
    }
}
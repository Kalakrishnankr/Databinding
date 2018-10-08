package com.beachpartnerllc.beachpartner.finder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.*
import androidx.lifecycle.ViewModel
import com.beachpartnerllc.beachpartner.cardstackview.SwipeDirection
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import com.beachpartnerllc.beachpartner.etc.model.rest.isLoading
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.user.Profile
import com.beachpartnerllc.beachpartner.user.auth.AuthState
import com.beachpartnerllc.beachpartner.user.state.State
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 17 Sep 2018 at 4:05 PM
 */
class FinderViewModel @Inject constructor(private val repo: FinderRepository) : ViewModel() {
	
	val loading = MutableLiveData<Boolean>()
	val selectedStatePosition = MutableLiveData<Int>()
	var search = MutableLiveData<Search>()
	var profile = MutableLiveData<Profile>()
	var singleProfile = MutableLiveData<Resource<Profile>>()
	val state = MutableLiveData<AuthState>()
	lateinit var stateList: List<State>
	lateinit var profileList: List<Profile>
	lateinit var bpprofileList : List<Profile>
	var itemProfile: Profile? = null
	
	
	fun setStatePosition(position: Int) {
		if (!::stateList.isInitialized) return
		val user = search.value!!
		user.stateId = stateList[position].stateId
		search.value = user
	}
	
	fun getStates() = map(repo.getStateList()) {
		loading.value = it.isLoading()
		if (it.isSuccess()) {
			stateList = it.data!!
			selectedStatePosition.value = selectedStatePosition.value
		}
		it
	}!!
	
	fun onAgeChange(min: Int, index: Int) {
		val ageValue = search.value!!
		if (index == 0) ageValue.minAge = min else ageValue.maxAge = min
		search.value = ageValue
	}
	
	fun isMaleActive() {
		val status = search.value!!
		status.isMaleActive = !status.isMaleActive
		search.value = status
	}
	
	fun isFemaleActive(){val status = search.value!!
		status.isFemaleActive = !status.isFemaleActive
		search.value = status
	}
	
	fun isCoachInclude(isStatus: Boolean) {
		val status = search.value!!
		status.isCoach = isStatus
		search.value = status
	}
	
	fun findProfiles() = map(repo.getProfiles(search.value!!)) {
		loading.value = it.isLoading()
		if (it.isSuccess()) profileList = it.data!!
		it
	}!!
	
	fun onSwipe(direction: SwipeDirection, index: Int) {
		when (direction) {
			SwipeDirection.Right -> requestFriendShip(profileList[index])
			SwipeDirection.Left -> rejectFriendShip(profileList[index])
			SwipeDirection.Top -> hifiRequest(profileList[index])
		}
	}
	
	private fun requestFriendShip(profile: Profile) {
		singleProfile = repo.actionRightSwipe(profile)
	}
	
	private fun rejectFriendShip(profile: Profile) {
		singleProfile = repo.actionLeftSwipe(profile)
	}
	
	private fun hifiRequest(profile: Profile) {
		singleProfile = repo.actionTopSwipe(profile)
	}
	
	fun blockPerson(profile: Profile) = map(repo.actionBlock(profile)) {
		loading.value = it.isLoading()
		if (it.isSuccess())
			it.data
		it
	}
	
	fun getProfile(userId: Int) = map(repo.getAccount(userId)) {
		loading.value = it.isLoading()
		if (it.isSuccess()) it.data!!
		it
	}
	
	fun getbpProfiles() = map(repo.getBluebpProfiles()){
		loading.value = it.isLoading()
		if(it.isSuccess()){
			bpprofileList = it.data!!
		}
		it
		
	}!!
	init {
		search.value = Search()
		profile.value = Profile()
	}
}
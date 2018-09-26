package com.beachpartnerllc.beachpartner.finder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.*
import androidx.lifecycle.ViewModel
import com.beachpartnerllc.beachpartner.cardstackview.SwipeDirection
import com.beachpartnerllc.beachpartner.etc.common.SingleLiveEvent
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import com.beachpartnerllc.beachpartner.etc.model.rest.isLoading
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.user.Gender
import com.beachpartnerllc.beachpartner.user.Profile
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 17 Sep 2018 at 4:05 PM
 */
class FinderViewModel @Inject constructor(private val repo: FinderRepository) : ViewModel() {
	
	val loading = MutableLiveData<Boolean>()
	val search = MutableLiveData<Search>()
	val selectedStatePosition = MutableLiveData<Int>()
	val multibar = MutableLiveData<Boolean>()
	val event = SingleLiveEvent<String>()
	var profile = MutableLiveData<ArrayList<Profile>>()
	lateinit var profileList: List<Profile>
	var singleProfile = MutableLiveData<Resource<Profile>>()
	
	
	/*fun setStatePosition(position: Int) {
		val user = search.value!!
		user.stateId = stateList[position].stateId
		search.value = user
	}*/
	
	/*fun getStates() = Transformations.map(findrepo.getStateList()) {
		loading.value = it.status == RequestState.LOADING
		
		if (it.isSuccess()) {
			stateList = it.data!!
		}
		it
	}!!*/
	
	fun onAgeChange(min: Int, index: Int) {
		val ageValue = search.value!!
		if (index == 0) ageValue.minAge = min else ageValue.maxAge = min
		search.value = ageValue
	}
	
	fun isCheck(isStatus: Boolean) {
		val status = search.value!!
		status.isCoach = isStatus
		search.value = status
	}
	
	fun isMale(isValue: Boolean) {
		val sex = search.value!!
		val result = sex.gender?.compareTo(Gender.FEMALE)
		if (result == 0) {
			sex.gender = Gender.BOTH
			search.value = sex
		} else {
			sex.gender = Gender.MALE
			search.value = sex
		}
	}
	
	fun isFemale(isValue: Boolean) {
		val sex = search.value!!
		val result = sex.gender?.compareTo(Gender.MALE)
		if (result == 0) {
			sex.gender = Gender.BOTH
			search.value = sex
		} else {
			sex.gender = Gender.FEMALE
			search.value = sex
		}
	}
	
	
	fun findProfiles() = map(repo.getProfiles()) {
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
	
	init {
		search.value = Search()
	}
}
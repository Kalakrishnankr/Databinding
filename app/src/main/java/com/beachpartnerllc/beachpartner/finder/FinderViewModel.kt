package com.beachpartnerllc.beachpartner.finder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.*
import androidx.lifecycle.ViewModel
import com.beachpartnerllc.beachpartner.etc.model.rest.isLoading
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.finder.cardstackview.SwipeDirection
import com.beachpartnerllc.beachpartner.finder.cardstackview.SwipeDirection.*
import com.beachpartnerllc.beachpartner.user.Profile
import com.beachpartnerllc.beachpartner.user.auth.AuthRepository
import com.beachpartnerllc.beachpartner.user.state.State
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 17 Sep 2018 at 4:05 PM
 */
class FinderViewModel @Inject constructor(
        private val repo: FinderRepository,
        private val authRepo: AuthRepository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    val selectedStatePosition = object : MutableLiveData<Int>() {
        override fun setValue(value: Int?) {
            super.setValue(value)
            value?.let { setStatePosition(value) }
        }
    }

    var search = MutableLiveData<Search>()
    var profile = MutableLiveData<Profile>()

    lateinit var stateList: List<State>
    lateinit var profileList: List<Profile>
    lateinit var bpprofileList: List<Profile>
    var itemProfile: Profile? = null

    fun setStatePosition(position: Int) {
        if (!::stateList.isInitialized) return
        val user = search.value!!
        user.stateId = stateList[position].stateId
        search.value = user
    }

    fun getStates() = map(authRepo.getStateList()) {
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

    fun isFemaleActive() {
        val status = search.value!!
        status.isFemaleActive = !status.isFemaleActive
        search.value = status
    }

    fun isCoachInclude(isStatus: Boolean) {
        val status = search.value!!
        status.isCoach = isStatus
        search.value = status
    }

    fun findProfiles() = map(repo.searchProfile(search.value!!)) {
        loading.value = it.isLoading()
        if (it.isSuccess()) profileList = it.data!!
        it
    }!!

    fun onSwipe(direction: SwipeDirection, index: Int) {
        when (direction) {
            Right -> repo.likeUser(profileList[index])
            Left -> repo.dislikeUser(profileList[index])
            Top -> repo.highFiveUser(profileList[index])
            Bottom -> { // Ignore
            }
        }
    }

    fun blockPerson(profile: Profile) = map(repo.blockUser(profile)) {
        loading.value = it.isLoading()
        if (it.isSuccess())
            it.data
        it
    }!!

    fun getProfile(userId: Int) = map(repo.getProfile(userId)) {
        loading.value = it.isLoading()
        if (it.isSuccess()) it.data!!
        it
    }!!

    fun getProfiles() = map(repo.getStripProfile()) {
        loading.value = it.isLoading()
        if (it.isSuccess()) {
            bpprofileList = it.data!!
        }
        it
    }!!

    init {
        search.value = Search()
        profile.value = Profile()
    }
}
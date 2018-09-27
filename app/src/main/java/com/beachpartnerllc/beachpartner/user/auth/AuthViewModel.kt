package com.beachpartnerllc.beachpartner.user.auth

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.*
import androidx.lifecycle.ViewModel
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.etc.base.BaseErrorEvent
import com.beachpartnerllc.beachpartner.etc.model.rest.RequestState
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.user.auth.AuthState.*
import com.beachpartnerllc.beachpartner.user.profile.Athlete
import com.beachpartnerllc.beachpartner.user.profile.Gender
import com.beachpartnerllc.beachpartner.user.profile.Profile
import com.beachpartnerllc.beachpartner.user.state.State
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Jun 2018 at 2:44 PM
 */
class AuthViewModel @Inject constructor(
	private val repo: AuthRepository,
	private val app: Application) : ViewModel() {
	
	val loading = MutableLiveData<Boolean>()
	val auth = MutableLiveData<Auth>()
	val profile = MutableLiveData<Profile>()
	val athlete = MutableLiveData<Athlete>()
	val nameError = MutableLiveData<BaseErrorEvent>()
	
	val state = MutableLiveData<AuthState>()
	val selectedStatePosition = MutableLiveData<Int>()
	private lateinit var stateList: List<State>
	
	val selectedGenderPosition = MutableLiveData<Int>()
	val signInValidate = MutableLiveData<Boolean>()
	val signUpValidate = MutableLiveData<Boolean>()
	val signUp2Validate = MutableLiveData<Boolean>()
	val isProfileEdit = MutableLiveData<Boolean>()
	val currentState = MutableLiveData<Boolean>()
	val topFinishesCount = MutableLiveData<Int>()
	
	val selectedExperiencePosition = MutableLiveData<Int>()
	val selectedPreferencePosition = MutableLiveData<Int>()
	val selectedPosPosition = MutableLiveData<Int>()
	val selectedHeightPosition = MutableLiveData<Int>()
	val selectedDistancePosition = MutableLiveData<Int>()
	
	fun signIn() = map(repo.signIn(auth.value!!)) {
		loading.value = it.status == RequestState.LOADING
		if (it.isSuccess()) {
			state.value = AUTHENTICATED
		}
	}!!
	
	fun signInSkipInitCount(): Long = if (signInValidate.value == true) 0 else 1
	
	fun signUpSkipInitCount(): Long = if (signUpValidate.value == true) 0 else 1
	
	fun signUp2SkipInitCount(): Long = if (signUp2Validate.value == true) 0 else 1
	
	fun register(): LiveData<Resource<Profile>> = map(repo.register(profile.value!!)) {
		loading.value = it.status == RequestState.LOADING
		if (it.isSuccess()) {
			state.value = REGISTERED
			autoSetEmail(it.data!!.email!!)
		}
		it
	}!!
	
	private fun autoSetEmail(email: String) {
		val auth = auth.value!!
		auth.email = email
		this.auth.value = auth
	}
	
	fun setStatePosition(position: Int) {
		if (!::stateList.isInitialized) return
		
		val user = profile.value!!
		user.stateId = stateList[position].stateId
		profile.value = user
	}
	
	fun getStates() = map(repo.getStateList()) {
		loading.value = it.status == RequestState.LOADING
		
		if (it.isSuccess()) {
			stateList = it.data!!
			selectedStatePosition.value = selectedStatePosition.value
		}
		it
	}!!
	
	fun editable(isEdit: Boolean) {
		isProfileEdit.value = isEdit
	}
	
	fun addTopFinish() {
		topFinishesCount.value = topFinishesCount.value!! + 1
	}
	
	fun removeTopFinish() {
		topFinishesCount.value = topFinishesCount.value!! - 1
	}
	
	fun setGender(position: Int) {
		val user = profile.value!!
		if (position == 0) user.gender = Gender.MALE
		else user.gender = Gender.FEMALE
		profile.value = user
	}
	
	fun setExperience(position: Int) {
		val user = athlete.value!!
		user.experience = app.resources.getStringArray(R.array.experience)[position]
		athlete.value = user
	}
	
	fun setPreference(position: Int?) {
		val user = athlete.value!!
		user.preference = app.resources.getStringArray(R.array.courtPreference)[position!!]
		athlete.value = user
	}
	
	fun setPosition(position: Int?) {
		val user = athlete.value!!
		user.position = app.resources.getStringArray(R.array.position)[position!!]
		athlete.value = user
	}
	
	fun setHeight(position: Int?) {
		val user = athlete.value!!
		user.height = app.resources.getStringArray(R.array.height)[position!!]
		athlete.value = user
	}
	
	fun setDistance(position: Int?) {
		val user = athlete.value!!
		user.distance = app.resources.getStringArray(R.array.distance)[position!!]
		athlete.value = user
	}
	
	fun setTopFinishes(topFinishes: ArrayList<String>) {
		val user =athlete.value!!
		user.topFinishes =topFinishes
		athlete.value =user
	}
	
	init {
		auth.value = Auth()
		profile.value = Profile()
		athlete.value = Athlete()
		topFinishesCount.value = 0
	}
}
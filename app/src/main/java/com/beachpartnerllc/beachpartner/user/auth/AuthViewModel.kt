package com.beachpartnerllc.beachpartner.user.auth

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.*
import androidx.lifecycle.ViewModel
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.etc.common.SingleLiveEvent
import com.beachpartnerllc.beachpartner.etc.model.rest.RequestState
import com.beachpartnerllc.beachpartner.etc.model.rest.isError
import com.beachpartnerllc.beachpartner.etc.model.rest.isLoading
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.user.auth.AuthState.*
import com.beachpartnerllc.beachpartner.user.profile.Athlete
import com.beachpartnerllc.beachpartner.user.profile.Coach
import com.beachpartnerllc.beachpartner.user.profile.Gender
import com.beachpartnerllc.beachpartner.user.profile.Profile
import com.beachpartnerllc.beachpartner.user.state.State
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Jun 2018 at 2:44 PM
 */
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val app: Application) : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val loginLoading = MutableLiveData<Boolean>()
    val auth = MutableLiveData<Auth>()
    val profile = MutableLiveData<Profile>()
    val athlete = MutableLiveData<Athlete>()
    val coach = MutableLiveData<Coach>()

    val state = MutableLiveData<AuthState>()

    val signInValidate = MutableLiveData<Boolean>()
    val signUpValidate = MutableLiveData<Boolean>()
    val signUp2Validate = MutableLiveData<Boolean>()
    val event = SingleLiveEvent<String>()

    val isProfileEdit = MutableLiveData<Boolean>()
    val currentState = MutableLiveData<Boolean>()
    val topFinishesCount = MutableLiveData<Int>()
    val profileValidate = MutableLiveData<Boolean>()
    val isTopFinishesSet = MutableLiveData<Boolean>()
    val imgAvailable = MutableLiveData<Boolean>()
    val imageUploadProgress = MutableLiveData<Int>()
    val videoUploadProgress = MutableLiveData<Int>()

    val stateList = MutableLiveData<List<State>>()
    val selectedStatePosition = object : MutableLiveData<Int>() {
        override fun setValue(value: Int?) {
            super.setValue(value)
            profile.value!!.stateId = stateList.value?.get(value!!)?.stateId
        }
    }

    val selectedExperiencePosition = object : MutableLiveData<Int>() {
        override fun setValue(value: Int?) {
            setExperience(value)
            super.setValue(value)
        }
    }

    val selectedPreferencePosition = object : MutableLiveData<Int>() {
        override fun setValue(value: Int?) {
            setPreference(value)
            super.setValue(value)
        }
    }

    val selectedPosPosition = object : MutableLiveData<Int>() {
        override fun setValue(value: Int?) {
            setPosition(value)
            super.setValue(value)
        }
    }

    val selectedHeightPosition = object : MutableLiveData<Int>() {
        override fun setValue(value: Int?) {
            setHeight(value)
            super.setValue(value)
        }
    }
    val selectedDistancePosition = object : MutableLiveData<Int>() {
        override fun setValue(value: Int?) {
            setDistance(value)
            super.setValue(value)
        }
    }

    val selectedFundingPosition = object : MutableLiveData<Int>() {
        override fun setValue(value: Int?) {
            setFunding(value)
            super.setValue(value)
        }
    }

    val selectedSharingPosition = object : MutableLiveData<Int>() {
        override fun setValue(value: Int?) {
            setSharing(value)
            super.setValue(value)
        }
    }

    val selectedGenderPosition = MutableLiveData<Int>()

    fun signIn() = map(repo.signIn(auth.value!!)) {
        loginLoading.value = it.isLoading()
        when {
            it.isSuccess() -> {
                event.value = it.message
                state.value = AUTHENTICATED
            }

            it.isError() -> event.value = it.message
        }
    }!!

    fun register() = map(repo.register(profile.value!!)) {
        loading.value = it.isLoading()
        when {
            it.isSuccess() -> {
                autoSetEmail(it.data!!.email!!)
                state.value = REGISTERED
                profile.value = Profile()
            }

            it.isError() -> event.value = it.message
        }
    }!!

    fun signInSkipInitCount(): Long = if (signInValidate.value == true) 0 else 1

    fun signUpSkipInitCount(): Long = if (signUpValidate.value == true) 0 else 1

    fun signUp2SkipInitCount(): Long = if (signUp2Validate.value == true) 0 else 1

    private fun autoSetEmail(email: String) {
        val auth = auth.value!!
        auth.email = email
        this.auth.value = auth
    }

    fun getStates() = map(repo.getStateList()) {
        loading.value = it.isLoading()

        when {
            it.isSuccess() -> {
                stateList.value = it.data!!
            }

            it.isError() -> event.value = it.message
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
    }

    fun setExperience(position: Int?) {
        val user = athlete.value!!
        user.experience = app.resources.getStringArray(R.array.experience)[position!!]
    }

    fun setPreference(position: Int?) {
        val user = athlete.value!!
        user.preference = app.resources.getStringArray(R.array.courtPreference)[position!!]
    }

    fun setPosition(position: Int?) {
        val user = athlete.value!!
        user.position = app.resources.getStringArray(R.array.position)[position!!]
    }

    fun setHeight(position: Int?) {
        val user = athlete.value!!
        user.height = app.resources.getStringArray(R.array.height)[position!!]
    }

    fun setDistance(position: Int?) {
        val user = athlete.value!!
        user.distance = app.resources.getStringArray(R.array.distance)[position!!]
    }

    fun setTopFinishes(topFinishes: ArrayList<String>) {
        val user = athlete.value!!
        user.topFinishes = topFinishes
    }

    private fun setFunding(position: Int?) {
        val user = coach.value!!
        user.funding = app.resources.getStringArray(R.array.yes_no)[position!!]
    }

    private fun setSharing(position: Int?) {
        val user = coach.value!!
        user.sharingAthletes = app.resources.getStringArray(R.array.yes_no)[position!!]
    }

    fun updateAthlete() = map(repo.update(athlete.value!!)) {
        loading.value = it.status == RequestState.LOADING
        if (it.isSuccess()) {
            Timber.e("Success")
        }
    }!!

    fun updateCoach() = map(repo.update(coach.value!!)) {
        loading.value = it.status == RequestState.LOADING
        if (it.isSuccess()) {
            Timber.e("Success")
        }
    }!!

    fun uploadImageToS3(path: String, extension: String) = map(repo.uploadFileToS3(path, extension)) {
        if (it.isSuccess()) {
            val user = athlete.value
            user!!.avatarUrl = it.data
            athlete.value = user
            imgAvailable.value = false
        } else {
            imageUploadProgress.value = it.code
            imgAvailable.value = true
        }
    }!!

    fun uploadVideoToS3(path: String, extension: String) = map(repo.uploadFileToS3(path, extension)) {
        if (it.isSuccess()) {
            val user = athlete.value
            user!!.video = it.data
            athlete.value = user
        } else {
            videoUploadProgress.value = it.code
        }
    }!!

    init {
        auth.value = Auth()
        profile.value = Profile()
        athlete.value = Athlete()
        coach.value = Coach()
        topFinishesCount.value = 0
        imgAvailable.value = false
    }
}
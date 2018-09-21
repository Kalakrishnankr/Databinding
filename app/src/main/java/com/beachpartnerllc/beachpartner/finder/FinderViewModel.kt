package com.beachpartnerllc.beachpartner.finder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beachpartnerllc.beachpartner.user.Gender
import com.beachpartnerllc.beachpartner.user.state.State
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
	private lateinit var stateList: List<State>
	
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
	
	fun onAgeChange(min : Int , index : Int) { val ageValue = search.value!!
		if(index == 0) ageValue.minAge = min else ageValue.maxAge = min
		search.value = ageValue }
	
	fun isCheck(isStatus : Boolean){ val status = search.value!!
		status.isCoach = isStatus
		search.value = status }
	
	fun isMale(isValue : Boolean){ val sex = search.value!!
		val result = sex.gender?.compareTo(Gender.FEMALE)
		if (result == 0) { sex.gender = Gender.BOTH
			search.value = sex }
		else{ sex.gender = Gender.MALE
			search.value = sex } }
	fun isFemale(isValue : Boolean) { val sex = search.value!!
		val result = sex.gender?.compareTo(Gender.MALE)
		if (result == 0) { sex.gender = Gender.BOTH
			search.value = sex
		}else{ sex.gender = Gender.FEMALE
			search.value = sex } }
		
	/*fun play(): LiveData<Resource<Search>> = Transformations.map(repo.play(search.value!!)){
		loading.value = it.status == RequestState.LOADING
		it
	}!!*/
	
	fun play(){}
	init {
		search.value = Search()
	}
}
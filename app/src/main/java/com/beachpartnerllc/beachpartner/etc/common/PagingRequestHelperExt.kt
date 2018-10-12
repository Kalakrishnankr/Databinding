package com.beachpartnerllc.beachpartner.etc.common

import androidx.lifecycle.MutableLiveData
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource

fun getErrorMessage(report: PagingRequestHelper.StatusReport): String {
	return PagingRequestHelper.RequestType.values().mapNotNull {
		report.getErrorFor(it)?.message
	}.first()
}

inline fun <reified T> PagingRequestHelper.createStatusLiveData(): MutableLiveData<Resource<List<T>>> {
	val liveData = MutableLiveData<Resource<List<T>>>()
	addListener { report ->
		when {
			report.hasRunning() -> liveData.postValue(Resource.loading())
			report.hasError() -> liveData.postValue(Resource.error(getErrorMessage(report)))
			else -> liveData.postValue(Resource.success())
		}
	}
	return liveData
}

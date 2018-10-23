package com.beachpartnerllc.beachpartner.etc.model.rest

import com.beachpartnerllc.beachpartner.etc.model.rest.RequestState.*
import retrofit2.Response

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 28 Nov 2017 at 3:14 PM
 */
class Resource<out T> constructor(
    val status: RequestState,
    val data: T? = null,
    val message: String? = null,
    val code: Int = 0) {

    companion object {
        fun <T> success(data: T? = null) = Resource(SUCCESS, data)

        fun <T> error(message: String? = null, data: T? = null, code: Int = 0) = Resource(ERROR, data, message, code)

	    fun <T> error(response: Response<T?>) = Resource(ERROR, null, response.errorBody().toString(), response.code())

        fun <T> loading(data: T? = null, code: Int = 0) = Resource(LOADING, data, null, code)

        fun <T> cached(message: String? = null, data: T?) = Resource(CACHED, data, message)
    }
}

inline fun <reified T> Resource<T>?.isSuccess(): Boolean = this != null && (status == SUCCESS || status == CACHED)

inline fun <reified T> Resource<T>?.isError(): Boolean = this != null && status == ERROR

inline fun <reified T> Resource<T>?.isLoading(): Boolean = this != null && status == LOADING
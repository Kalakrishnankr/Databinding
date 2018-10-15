package com.beachpartnerllc.beachpartner.etc.model.rest

import com.beachpartnerllc.beachpartner.etc.model.rest.RequestState.*

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 28 Nov 2017 at 3:14 PM
 */
class Resource<out T> private constructor(val status: RequestState?, val data: T?, val message: String?, val code: Int) {
	companion object {
		fun <T> success(data: T? = null) = Resource(SUCCESS, data, null, 0)
		
		fun <T> error(msg: String? = null, data: T? = null) = Resource(ERROR, data, msg, 0)
		
		fun <T> loading(data: T? = null, code: Int = 0) = Resource(LOADING, data, null, code)
		
		fun <T> cached(msg: String? = null, data: T?) = Resource(CACHED, data, null, 0)
	}
}
package com.beachpartnerllc.beachpartner.etc.model

import com.beachpartnerllc.beachpartner.etc.model.Status.*

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 28 Nov 2017 at 3:14 PM
 */
class Resource<out T>(val status: Status?, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T) = Resource(SUCCESS, data, null)

        fun <T> error(msg: String?, data: T? = null) = Resource(ERROR, data, msg)

        fun <T> loading(data: T? = null) = Resource(LOADING, data, null)
    }

    fun isSuccessful() = status != null && status == SUCCESS
}
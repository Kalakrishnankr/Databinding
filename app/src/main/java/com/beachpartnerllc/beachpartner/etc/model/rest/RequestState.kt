package com.beachpartnerllc.beachpartner.etc.model.rest

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 28 Nov 2017 at 3:16 PM
 */
enum class RequestState {
    SUCCESS, // success network request status
    ERROR,   // failure network request status
    LOADING, // progress network request status
    CACHED;   // Network failed, contains local data

    fun isLoading(): Boolean = this == LOADING
}
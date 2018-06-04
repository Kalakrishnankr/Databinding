package com.beachpartnerllc.beachpartner.etc.model

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 28 Nov 2017 at 3:16 PM
 */
enum class State {
    SUCCESS, // success network request state
    ERROR,   // failure network request state
    LOADING  // progress network request state
}
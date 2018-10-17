package com.beachpartnerllc.beachpartner.finder

import com.beachpartnerllc.beachpartner.user.profile.Gender


/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 14 Sep 2018 at 3:46 PM
 */
data class Search(
    var minAge: Int? = null,
    var maxAge: Int? = null,
    var stateId: Int? = null,
    var gender: Gender? = null,
    var isCoach: Boolean = false,
    var isMaleActive: Boolean = false,
    var isFemaleActive: Boolean = false) {

    fun getGenderStatus() = when {
        isMaleActive && isFemaleActive -> "BOTH"
        isMaleActive -> "MALE"
        else -> "FEMALE"
    }
}


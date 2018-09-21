package com.beachpartnerllc.beachpartner.finder

import com.beachpartnerllc.beachpartner.user.Gender

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 14 Sep 2018 at 3:46 PM
 */
data class Search (
	var minAge: Int? = null,
	var maxAge: Int? = null,
	var stateId: Int? = null,
	var gender: Gender? = null,
	var isCoach: Boolean = false
)


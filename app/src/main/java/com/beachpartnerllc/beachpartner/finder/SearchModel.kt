package com.beachpartnerllc.beachpartner.finder

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 14 Sep 2018 at 3:46 PM
 */
data class SearchModel (
	var minAge: Int? = null,
	var maxAge: Int? = null,
	var state: String? = null,
	var gender: String? = null,
	var includeCoach: Boolean? = null

)
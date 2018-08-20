package com.beachpartnerllc.beachpartner.user.auth

import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.etc.base.BaseErrorEvent

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 17 Aug 2018 at 3:16 PM
 */
enum class EmailErrorEvent(private val res: Int) : BaseErrorEvent {
	NONE(0),
	INVALID_FORMAT(R.string.invalid_email);
	
	override fun getErrorResources() = res
}
package com.beachpartnerllc.beachpartner.messaging

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 01 Oct 2018 at 12:16 PM
 */
data class Chat(
	override var id: String? = null,
	val members: List<Int>? = null,
	val recent: Message? = null) : HasId {
	var avatarUrl: String? = null
	var title: String? = null
}
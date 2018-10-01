package com.beachpartnerllc.beachpartner.messaging

import android.text.format.DateFormat
import com.google.firebase.Timestamp

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 01 Oct 2018 at 5:10 PM
 */
data class Message(
	var content: String? = null,
	var sentAt: Timestamp? = null,
	var senderId: Int? = null) {
	
	fun displayTime(): String? = if (sentAt != null) {
		DateFormat.format("h:mm a", sentAt!!.toDate()).toString()
	} else null
}
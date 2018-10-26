package com.beachpartnerllc.beachpartner.messaging

import com.google.firebase.firestore.Exclude

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 01 Oct 2018 at 12:16 PM
 */
data class Chat(
    @set:Exclude @get:Exclude override var id: String? = null,
    val members: List<Int>? = null,
    val recent: Message? = null) : HasId {
    @set:Exclude @get:Exclude var avatarUrl: String? = null
    @set:Exclude @get:Exclude var title: String? = null
}
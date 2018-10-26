package com.beachpartnerllc.beachpartner.messaging

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 01 Oct 2018 at 5:19 PM
 */
data class Member(
    override var id: String? = null,
    var name: String? = null,
    var avatarUrl: String? = null
) : HasId
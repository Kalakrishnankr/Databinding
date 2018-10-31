package com.beachpartnerllc.beachpartner.user.state

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 20 Aug 2018 at 11:34 AM
 */
@Entity(tableName = "state")
data class State(
    @PrimaryKey val stateId: Int,
    val stateName: String
)
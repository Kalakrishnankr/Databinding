package com.beachpartnerllc.beachpartner.event

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 17 Sep 2018 at 3:06 PM
 */
@Entity(tableName = "events")
data class Event(
        @PrimaryKey val id: Int,
        val title: String,
        val eventDate: Date
)
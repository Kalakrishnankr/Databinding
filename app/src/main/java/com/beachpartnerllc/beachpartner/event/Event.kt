package com.beachpartnerllc.beachpartner.event

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 17 Sep 2018 at 3:06 PM
 */
@Entity(tableName = "events")
data class Event(
    @PrimaryKey val eventId: Int,
    val title: String,
    val location: String,
    val venue: String,
    val admin: String,
    @ColumnInfo(name = "team_size") val teamSize: String,
    @ColumnInfo(name = "event_start_date") val eventStartDate: Date,
    @ColumnInfo(name = "event_end_date") val eventEndDate: Date,
    @ColumnInfo(name = "reg_start_date") val regStartDate: Date,
    @ColumnInfo(name = "reg_end_date") val regEndDate: Date,
    val status: EventStatus
)

enum class EventStatus {
    REGISTERED, INVITATION_SENT, INVITATION_RECEIVED, REGISTRATION_PENDING;

    fun isSent() = this == INVITATION_SENT
}

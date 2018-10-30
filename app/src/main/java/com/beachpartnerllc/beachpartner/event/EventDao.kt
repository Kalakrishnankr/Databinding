package com.beachpartnerllc.beachpartner.event

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 18 Sep 2018 at 2:26 PM
 */
@Dao
interface EventDao {
    @Query("SELECT * from events WHERE :eventDate BETWEEN event_start_date AND event_end_date")
    fun eventsByDate(eventDate: Date): DataSource.Factory<Int, Event>

    @Query("DELETE FROM events WHERE :eventDate BETWEEN event_start_date AND event_end_date")
    fun deleteByDate(eventDate: Date)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<Event>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: Event)

    @Query("SELECT * FROM events WHERE eventId = :eventId")
    fun getEvent(eventId: Int): LiveData<Event>

    @Query("SELECT * FROM events WHERE event_start_date BETWEEN :now AND :eventDate")
    fun getEventsForNext(eventDate: Date, now: Date = Calendar.getInstance().time): LiveData<List<Event>>

    @Query("SELECT * FROM events WHERE status = :request")
    fun getEvents(request: EventStatus): LiveData<List<Event>>
}
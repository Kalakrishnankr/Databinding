package com.beachpartnerllc.beachpartner.event

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
    @Query("SELECT * from events WHERE eventDate = :eventDate")
    fun eventsByDate(eventDate: Date): DataSource.Factory<Int, Event>

    @Query("DELETE FROM events WHERE eventDate = :eventDate")
    fun deleteByDate(eventDate: Date)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<Event>)
}
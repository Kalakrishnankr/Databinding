package com.beachpartnerllc.beachpartner.etc.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.beachpartnerllc.beachpartner.connection.ConnectionDao
import com.beachpartnerllc.beachpartner.event.Event
import com.beachpartnerllc.beachpartner.event.EventDao
import com.beachpartnerllc.beachpartner.user.profile.Profile
import com.beachpartnerllc.beachpartner.user.state.State
import com.beachpartnerllc.beachpartner.user.state.StateDao

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 04 Dec 2017 at 3:24 PM
 */
@TypeConverters(Converters::class)
@Database(
    entities = [State::class, Event::class, Profile::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun state(): StateDao

    abstract fun event(): EventDao

    abstract fun connection(): ConnectionDao

    companion object {
        const val DATABASE_NAME = "bp.db"
    }
}
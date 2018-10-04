package com.beachpartnerllc.beachpartner.etc.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.beachpartnerllc.beachpartner.user.state.State
import com.beachpartnerllc.beachpartner.user.state.StateDao

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 04 Dec 2017 at 3:24 PM
 */
@TypeConverters(Converters::class)
@Database(entities = [State::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
	abstract fun stateDao(): StateDao
	//abstract fun profileDao() : ProfileDao
	
	companion object {
		const val DATABASE_NAME = "bp.db"
	}
}
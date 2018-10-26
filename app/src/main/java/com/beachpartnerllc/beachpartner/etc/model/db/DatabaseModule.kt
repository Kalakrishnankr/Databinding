package com.beachpartnerllc.beachpartner.etc.model.db

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 17 May 2018 at 1:47 PM
 */
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun roomProvider(app: Application): AppDatabase {
        // TODO: swap out in memory database implementation for production
        /*return Room.databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()*/

        return Room.inMemoryDatabaseBuilder(app, AppDatabase::class.java)
            .fallbackToDestructiveMigration()
            .build()
    }
}
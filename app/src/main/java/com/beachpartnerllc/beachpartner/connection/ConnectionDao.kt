package com.beachpartnerllc.beachpartner.connection

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.beachpartnerllc.beachpartner.user.Profile

@Dao
interface ConnectionDao {
	@Query("SELECT * FROM connections")
	fun getConnections(): LiveData<List<Profile>>
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(items: List<Profile>)
}

package com.beachpartnerllc.beachpartner.user.state

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 20 Aug 2018 at 11:54 AM
 */
@Dao
interface StateDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertStates(crops: List<State>)
	
	@Query("SELECT * FROM stateId")
	fun getAllStates(): LiveData<List<State>>
}
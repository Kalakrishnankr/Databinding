package com.beachpartnerllc.beachpartner.etc.model.db

import androidx.room.TypeConverter
import java.util.*


/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 25 Oct 2017 at 10:50 AM
 */
class Converters {
	@TypeConverter
	fun longToDate(value: Long) = Date(value)
	
	@TypeConverter
	fun dateToLong(date: Date) = date.time
}
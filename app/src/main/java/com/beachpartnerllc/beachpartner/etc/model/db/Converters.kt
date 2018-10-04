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
	
//	@TypeConverter
//	fun genderToString(value : Gender) = value.toString()
//
//	@TypeConverter
//	fun stringToGender(gender : String) = Gender.valueOf(gender)
//
//	@TypeConverter
//	fun usertypeToString(value: UserType) = value.toString()
//
//	@TypeConverter
//	fun stringToUsertype(userType: String)= UserType.valueOf(userType)
	

}
package com.beachpartnerllc.beachpartner.etc.model.db

import androidx.room.TypeConverter
import com.beachpartnerllc.beachpartner.event.EventStatus
import com.beachpartnerllc.beachpartner.user.profile.Gender
import com.beachpartnerllc.beachpartner.user.profile.UserType
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

    @TypeConverter
    fun genderToString(value: Gender) = value.toString()

    @TypeConverter
    fun stringToGender(value: String) = Gender.valueOf(value)

    @TypeConverter
    fun userTypeToString(value: UserType) = value.toString()

    @TypeConverter
    fun stringToUserType(value: String) = UserType.valueOf(value)

    @TypeConverter
    fun eventStatusToString(value: EventStatus) = value.toString()

    @TypeConverter
    fun stringToEventStatus(value: String) = EventStatus.valueOf(value)
}
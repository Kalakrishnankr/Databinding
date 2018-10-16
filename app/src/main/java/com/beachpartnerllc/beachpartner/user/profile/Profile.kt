package com.beachpartnerllc.beachpartner.user.profile

import com.beachpartnerllc.beachpartner.etc.common.isEmail
import com.beachpartnerllc.beachpartner.etc.common.isMobile
import com.beachpartnerllc.beachpartner.etc.common.isName
import com.beachpartnerllc.beachpartner.etc.common.isPassword
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

open class Profile(
	open val userId: Int = -1,
	open var firstName: String? = null,
	open var lastName: String? = null,
	open var stateId: Int? = null,
	open var gender: Gender? = null,
	open var userType: UserType? = null,
	open var email: String? = null,
	open var mobile: String? = null,
	open var password: String? = null,
	open var dob: String? = null,
	var image: String? = null,
	var video: Any? = null
) {
	
	fun isFirstNameValid() = firstName.isName()
	
	fun isLastNameValid() = lastName.isName()
	
	fun isStateValid() = stateId != null
	
	fun isGenderValid() = gender != null
	
	fun isUserTypeValid() = userType != null
	
	fun isValid() = isFirstNameValid() && isLastNameValid() && isStateValid() && isGenderValid() &&
		isUserTypeValid()
	
	fun isEmailValid() = email.isEmail()
	
	fun isMobileValid() = mobile.isMobile()
	
	fun isPasswordValid() = password.isPassword()
	
	fun isDobValid() = try {
		val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
		sdf.isLenient = false
		sdf.parse(dob)
		true
	} catch (e: ParseException) {
		false
	} catch (e: NullPointerException) {
		false
	}
	
	fun isValid2() = isEmailValid() && isMobileValid() && isPasswordValid() && isDobValid()
	
	fun isAthlete() = userType == UserType.ATHLETE
	
	fun isMale() = gender == Gender.MALE
	
	fun isFemale() = gender == Gender.FEMALE
	
	fun isCoach() = userType == UserType.COACH
}

data class Session(val profile: Profile, val sessionId: String)
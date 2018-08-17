package com.beachpartnerllc.beachpartner.user

import com.beachpartnerllc.beachpartner.etc.common.isEmail
import com.beachpartnerllc.beachpartner.etc.common.isMobile
import com.beachpartnerllc.beachpartner.etc.common.isName
import com.beachpartnerllc.beachpartner.etc.common.isPassword
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class Profile(
    var firstName: String? = null,
    var lastName: String? = null,
    var state: String? = null,
    var gender: Gender? = null,
    var userType: UserType? = null,
    var email: String? = null,
    var mobile: String? = null,
    var password: String? = null,
    var dob: String? = null
) {

    fun isFirstNameValid() = firstName.isName()

    fun isLastNameValid() = lastName.isName()

    fun isStateValid() = state != null

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

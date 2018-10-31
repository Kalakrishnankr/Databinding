package com.beachpartnerllc.beachpartner.user.profile

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.beachpartnerllc.beachpartner.etc.common.isEmail
import com.beachpartnerllc.beachpartner.etc.common.isMobile
import com.beachpartnerllc.beachpartner.etc.common.isName
import com.beachpartnerllc.beachpartner.etc.common.isPassword
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "Connections")
open class Profile(
    @PrimaryKey var userId: Int = -1,
    var firstName: String? = null,
    var lastName: String? = null,
    var stateId: Int? = null,
    var gender: Gender? = null,
    var userType: UserType? = null,
    var email: String? = null,
    var mobile: String? = null,
    var password: String? = null,
    var dob: String? = null,
    var avatarUrl: String? = null,
    var videoUrl: String? = null,
    var age: Int? = null,
    var status: String? = null
) {
    @Ignore
    var dateOfBirth: Date? = null
    val fullName: String? get() = "$firstName $lastName"

    fun isFirstNameValid() = firstName.isName()

    fun isLastNameValid() = lastName.isName()

    fun isStateValid() = stateId != null

    fun isGenderValid() = gender != null

    fun isUserTypeValid() = userType != null

    fun isValid() = isFirstNameValid() && isLastNameValid() && isStateValid()
            && isGenderValid() && isUserTypeValid()

    fun isEmailValid() = email.isEmail()

    fun isMobileValid() = mobile.isMobile()

    fun isPasswordValid() = password.isPassword()

    fun isDobValid() = try {
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        sdf.isLenient = false
        dateOfBirth = sdf.parse(dob)
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

    override fun hashCode(): Int {
        var result = userId
        result = 31 * result + (firstName?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + (stateId ?: 0)
        result = 31 * result + (gender?.hashCode() ?: 0)
        result = 31 * result + (userType?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (mobile?.hashCode() ?: 0)
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + (dob?.hashCode() ?: 0)
        result = 31 * result + (avatarUrl?.hashCode() ?: 0)
        result = 31 * result + (videoUrl?.hashCode() ?: 0)
        result = 31 * result + (age ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (dateOfBirth?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Profile) return false

        if (userId != other.userId) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (stateId != other.stateId) return false
        if (gender != other.gender) return false
        if (userType != other.userType) return false
        if (email != other.email) return false
        if (mobile != other.mobile) return false
        if (password != other.password) return false
        if (dob != other.dob) return false
        if (avatarUrl != other.avatarUrl) return false
        if (videoUrl != other.videoUrl) return false
        if (age != other.age) return false
        if (status != other.status) return false
        if (dateOfBirth != other.dateOfBirth) return false

        return true
    }
}

data class Session(val profile: Profile, val sessionId: String)
package com.beachpartnerllc.beachpartner.etc.model.pref

import android.content.SharedPreferences
import androidx.core.content.edit
import com.beachpartnerllc.beachpartner.user.profile.Session
import com.beachpartnerllc.beachpartner.user.profile.UserType
import javax.inject.Singleton

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 05 Dec 2017 at 11:38 AM
 */
@Singleton
class Preference(private val pref: SharedPreferences) {
    var firstVisit: Boolean
        get() = pref.getBoolean(PREF_FIRST_VISIT, true)
        set(value) = pref.edit { putBoolean(PREF_FIRST_VISIT, value) }

    val sessionId: String?
        get() = pref.getString(PREF_SESSION_ID, null)

    val isLoggedIn: Boolean
        get () = pref.getBoolean(PREF_IS_LOGGED_IN, false)

    val userType: UserType?
        get() = UserType.valueOf(pref.getString(PREF_USER_TYPE, UserType.ATHLETE.toString()))

    val userId: Int
        get() = pref.getInt(PREF_USER_ID, 1)

    fun clear() {
        pref.edit().clear().apply()
    }

    fun setSession(session: Session) {
        val profile = session.profile
        pref.edit {
            putInt(PREF_USER_ID, profile.userId)
            putInt(PREF_STATE_ID, profile.stateId!!)
            putString(PREF_SESSION_ID, session.sessionId)
            putString(PREF_FIRST_NAME, profile.firstName)
            putString(PREF_LAST_NAME, profile.lastName)
            putString(PREF_GENDER, profile.gender!!.name)
            putString(PREF_USER_TYPE, profile.userType!!.name)
            putString(PREF_EMAIL, profile.email)
            putString(PREF_MOBILE, profile.mobile)
            putString(PREF_DOB, profile.dob)
            putBoolean(PREF_IS_LOGGED_IN, true)
        }
    }

    companion object {
        private const val PREF_USER_ID = "pref_user_id"
        private const val PREF_FIRST_NAME = "pref_first_name"
        private const val PREF_LAST_NAME = "pref_last_name"
        private const val PREF_STATE_ID = "pref_state_id"
        private const val PREF_GENDER = "PREF_GENDER"
        private const val PREF_USER_TYPE = "pref_user_type"
        private const val PREF_EMAIL = "pref_email"
        private const val PREF_MOBILE = "pref_mobile"
        private const val PREF_DOB = "PREF_DOB"
        private const val PREF_AVATAR = "pref_avatar"

        private const val PREF_SESSION_ID = "pref_session_id"
        private const val PREF_IS_LOGGED_IN = "pref_is_logged_in"
        private const val PREF_FIRST_VISIT = "pref_first_visit"

        const val SHARED_PREF_NAME = "shared_pref"
    }
}
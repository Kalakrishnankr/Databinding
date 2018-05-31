package com.beachpartnerllc.beachpartner.etc.model

import android.content.SharedPreferences
import javax.inject.Singleton

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 05 Dec 2017 at 11:38 AM
 */
@Singleton
class Preference(private val mPref: SharedPreferences) {
    var firstVisit: Boolean
        get() = mPref.getBoolean(PREF_FIRST_VISIT, true)
        set(value) = mPref.edit().putBoolean(PREF_FIRST_VISIT, value).apply()

    var sessionKey: String?
        get() = mPref.getString(PREF_SESSION_KEY, null)
        set(value) = mPref.edit().putString(PREF_SESSION_KEY, value).apply()

    var isLoggedIn: Boolean
        get () = mPref.getBoolean(PREF_IS_LOGGED_IN, false)
        set(value) = mPref.edit().putBoolean(PREF_IS_LOGGED_IN, value).apply()

    val email: String
        get() = mPref.getString(PREF_EMAIL, null)

    val password: String
        get() = mPref.getString(PREF_PASSWORD, null)

    fun getName(): String? = mPref.getString(PREF_NAME, null)

    fun clear() {
        mPref.edit().clear().apply()
    }

    companion object {
        const val SHARED_PREF_NAME = "sp_med_expertz"
        private const val PREF_ID = "pref_id"
        private const val PREF_NAME = "pref_name"
        private const val PREF_EMAIL = "pref_email"
        private const val PREF_MOBILE = "pref_mobile"
        private const val PREF_DOB = "PREF_DOB"
        private const val PREF_GENDER = "PREF_GENDER"
        private const val PREF_SESSION_KEY = "pref_session_key"
        private const val PREF_AVATAR = "pref_avatar"
        private const val PREF_IS_LOGGED_IN = "pref_is_logged_in"
        private const val PREF_FIRST_VISIT = "pref_first_visit"
        private const val PREF_PASSWORD = "pref_password"
    }
}
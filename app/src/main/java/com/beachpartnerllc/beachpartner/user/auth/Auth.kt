package com.beachpartnerllc.beachpartner.user.auth

import android.provider.Settings
import com.beachpartnerllc.beachpartner.etc.common.isEmail
import com.beachpartnerllc.beachpartner.etc.common.isPassword
import com.google.gson.annotations.SerializedName

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Jun 2018 at 2:40 PM
 */
data class Auth(
        @SerializedName("username")
        var email: String? = null,

        var password: String? = null,

        val deviceId: String = Settings.Secure.ANDROID_ID,
        val deviceToken: String? = null,
        val deviceType: String = "Android",
        val fcmToken: String? = null,
        val rememberMe: Boolean = true
) {
    fun isEmailValid() = email.isEmail()

    fun isPasswordValid() = password.isPassword()

    fun isValid() = isEmailValid() && isPasswordValid()
}
package com.beachpartnerllc.beachpartner.user.auth

import com.beachpartnerllc.beachpartner.etc.common.isEmail
import com.beachpartnerllc.beachpartner.etc.common.isPassword

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Jun 2018 at 2:40 PM
 */
data class Auth(var email: String? = null, var password: String? = null) {
    fun isEmailValid() = email.isEmail()

    fun isPasswordValid() = password.isPassword()

    fun isValid() = isEmailValid() && isPasswordValid()
}
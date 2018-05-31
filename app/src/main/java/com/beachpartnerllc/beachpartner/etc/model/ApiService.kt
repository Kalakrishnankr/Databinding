package com.beachpartnerllc.beachpartner.etc.model


/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 27 Nov 2017 at 2:48 PM
 */
interface ApiService {

    companion object {
        const val URL_BASE = "https://oii8bb2dlf.execute-api.us-east-1.amazonaws.com/dev/"
        const val NO_AUTH = "No-Auth"
        private const val HEADER_NO_AUTH = "$NO_AUTH: true"
    }
}
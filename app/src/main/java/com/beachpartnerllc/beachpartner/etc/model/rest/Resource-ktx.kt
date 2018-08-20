package com.beachpartnerllc.beachpartner.etc.model.rest

import com.beachpartnerllc.beachpartner.etc.model.rest.RequestState.*

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 03 Jul 2018 at 11:58 AM
 */

inline fun <reified T> Resource<T>?.isSuccess(): Boolean = this != null && (status == SUCCESS || status == CACHED)
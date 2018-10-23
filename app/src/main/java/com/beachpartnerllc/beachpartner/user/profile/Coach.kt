package com.beachpartnerllc.beachpartner.user.profile

/**
 * @author Abraham Mathew <abraham.mathew@seqato.com>
 * @created on 15 Oct 2018 at 11:05 AM
 */
data class Coach(
    var college: String? = null,
    var description: String? = null,
    var yearsRunning: String? = null,
    var athleteNumber: String? = null,
    var programsOffered: String? = null,
    var division: String? = null,
    var funding: String? = null,
    var sharingAthletes: String? = null) : Profile()

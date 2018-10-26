package com.beachpartnerllc.beachpartner.user.profile

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 26 Sep 2018 at 11:42 AM
 */
data class Athlete(
    var experience: String? = null,
    var preference: String? = null,
    var position: String? = null,
    var height: String? = null,
    var distance: String? = null,
    var highSchool: String? = null,
    var indoorClub: String? = null,
    var collegeBeach: String? = null,
    var collegeIndoor: String? = null,
    var sandRecruitsNo: String? = null,
    var topFinishes: ArrayList<String> = ArrayList(),
    var video: Any? = null
) : Profile()
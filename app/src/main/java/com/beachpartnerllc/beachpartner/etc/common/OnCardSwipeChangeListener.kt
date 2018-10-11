package com.beachpartnerllc.beachpartner.etc.common

import com.beachpartnerllc.beachpartner.finder.cardstackview.SwipeDirection

/**
 * @author Samuel Robert <samuel.robert></samuel.robert>@seqato.com>
 * @created on 25 Sep 2018 at 1:55 PM
 */
interface OnCardSwipeChangeListener{
	fun onDirection(direction: SwipeDirection, index : Int)
}

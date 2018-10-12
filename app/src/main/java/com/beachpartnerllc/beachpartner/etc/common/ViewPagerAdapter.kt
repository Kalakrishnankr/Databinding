package com.beachpartnerllc.beachpartner.etc.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 17 Sep 2018 at 4:42 PM
 */
class ViewPagerAdapter(
	fm: FragmentManager,
	private val items: ArrayList<Fragment>,
	private val titles: ArrayList<String>? = null) : FragmentPagerAdapter(fm) {
	override fun getItem(position: Int) = items[position]
	
	override fun getCount() = items.size
	
	override fun getPageTitle(position: Int) = titles?.get(position)
}

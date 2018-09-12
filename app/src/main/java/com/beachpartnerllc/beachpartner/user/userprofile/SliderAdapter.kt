package com.beachpartnerllc.beachpartner.user.userprofile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 12 Sep 2018 at 12:28 PM
 */
class SliderAdapter(fm: FragmentManager?, val numberOfTabs:Int, val titles: Array<CharSequence>) : FragmentPagerAdapter(fm) {
	override fun getItem(position: Int): Fragment? {
		if(position==0){
			val basicInfoFragment = BasicInfoFragment()
			
			return basicInfoFragment
		}
		else{
			val moreInfoFragment = MoreInfoFragment()
			
			return moreInfoFragment
		}

	}
	
	override fun getPageTitle(position: Int): CharSequence {
		return titles[position]
	}
	override fun getCount(): Int {
		return numberOfTabs
	}
}
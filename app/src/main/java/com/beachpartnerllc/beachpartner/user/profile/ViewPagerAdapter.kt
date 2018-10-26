package com.beachpartnerllc.beachpartner.user.profile

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 12 Sep 2018 at 12:28 PM
 */
class ViewPagerAdapter(
    fm: FragmentManager,
    private val items: List<Fragment>,
    private val titles: List<String>? = null
) : FragmentPagerAdapter(fm) {

    private var mCurrentPosition = -1

    override fun getItem(position: Int): Fragment? {
        if (position < 0) return null
        return items[position]
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        if (position != mCurrentPosition) {
            val fragment = `object` as Fragment
            val pager = container as CustomViewPager
            if (fragment != null && fragment.view != null) {
                mCurrentPosition = position
                pager.measureCurrentView(fragment.view!!)
            }
        }
    }

    override fun getPageTitle(position: Int) = titles?.get(position)

    override fun getCount() = items.size
}
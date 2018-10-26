package com.beachpartnerllc.beachpartner.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.CalendarBinding
import com.beachpartnerllc.beachpartner.etc.common.ViewPagerAdapter
import com.beachpartnerllc.beachpartner.etc.common.bind

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 17 Sep 2018 at 3:42 PM
 */
class CalendarFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: CalendarBinding = inflater.bind(R.layout.fragment_calendar, container)
        binding.adapter = ViewPagerAdapter(
            childFragmentManager,
            arrayListOf(MasterCalendarFragment(), MasterCalendarFragment()),
            arrayListOf("Master Calendar", "My Calendar")
        )
        return binding.root
    }
}
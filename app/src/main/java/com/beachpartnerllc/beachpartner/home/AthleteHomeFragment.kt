package com.beachpartnerllc.beachpartner.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.AthleteHomeFragmentBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.bind

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 13 Jun 2018 at 4:13 PM
 */
class AthleteHomeFragment : BaseFragment() {
    private lateinit var binding: AthleteHomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflater.bind(R.layout.fragment_athlete_home, container)
        return binding.root
    }
}
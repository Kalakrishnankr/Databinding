package com.beachpartnerllc.beachpartner.user.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.AthleteProfileBinding
import com.beachpartnerllc.beachpartner.etc.common.bind

class AthleteProfileFragment : Fragment() {
	private lateinit var binding: AthleteProfileBinding
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = container!!.bind(R.layout.fragment_athlete_profile)
		binding.tabs.setupWithViewPager(binding.pager)
		binding.adapter = SliderAdapter(childFragmentManager, 2, resources.getTextArray(R.array.profile_titles))
		return binding.root
	}
}

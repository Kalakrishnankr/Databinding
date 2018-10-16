package com.beachpartnerllc.beachpartner.user.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.CoachMoreInfoBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.user.auth.AuthViewModel
import javax.inject.Inject


class CoachMoreInfoFragment : BaseFragment() {
	private lateinit var binding: CoachMoreInfoBinding
	private lateinit var vm: AuthViewModel
	@Inject lateinit var factory: ViewModelProvider.Factory
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		binding = inflater.bind(R.layout.fragment_coach_more_info, container)
		return binding.root
	}
	
	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		vm = getViewModel(factory)
		binding.vm = vm
		binding.setLifecycleOwner(viewLifecycleOwner)
	}
}

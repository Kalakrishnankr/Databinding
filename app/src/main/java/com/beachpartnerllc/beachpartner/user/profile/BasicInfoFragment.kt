package com.beachpartnerllc.beachpartner.user.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.BasicInfoBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.user.auth.AuthViewModel
import javax.inject.Inject


class BasicInfoFragment : BaseFragment() {
	@Inject lateinit var factory: ViewModelProvider.Factory
	private lateinit var binding : BasicInfoBinding
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = inflater!!.bind(R.layout.fragment_basic_info,container)
		return binding.root
	}
	
	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		
		val vm: AuthViewModel = getViewModel(factory)
		binding.vm = vm
		binding.setLifecycleOwner(viewLifecycleOwner)
		
		vm.selectedStatePosition.observe(this, Observer {
			it?.let { vm.setStatePosition(it) }
		})
		
		vm.getStates().observe(viewLifecycleOwner, Observer { it ->
			if (it.isSuccess()) {
				binding.spinnerState.adapter = ArrayAdapter(
					context!!,
					R.layout.simple_spinner_item_1line,
					it.data?.map { it.stateName }!!)
			}
		})
	}


}

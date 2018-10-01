package com.beachpartnerllc.beachpartner.finder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.SearchFragmentBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import javax.inject.Inject


/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 11 Sep 2018 at 1:01 PM
 */
class SearchFragment : BaseFragment()  {
	@Inject lateinit var factory: ViewModelProvider.Factory
	private lateinit var binding: SearchFragmentBinding
	private lateinit var vm: FinderViewModel
	
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false)
		return binding.root
	}
	
	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		
		vm = getViewModel(factory)
		binding.vm = vm
		binding.setLifecycleOwner(viewLifecycleOwner)
		
		vm.selectedStatePosition.observe(viewLifecycleOwner, Observer {
			it?.let { vm.setStatePosition(it) }
		})
		
		vm.getStates().observe(viewLifecycleOwner, Observer { it ->
			if (it.isSuccess()) {
				binding.stateACS.adapter = ArrayAdapter(
					context!!,
					android.R.layout.simple_dropdown_item_1line,
					it.data?.map { it.stateName }!!)
			}
		})
		
	}
}
package com.beachpartnerllc.beachpartner.user.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.forEach
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.MoreInfoBinding
import com.beachpartnerllc.beachpartner.databinding.TopFinishesBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.user.auth.AuthViewModel
import javax.inject.Inject


class MoreInfoFragment : BaseFragment() {
	private lateinit var binding: MoreInfoBinding
	private lateinit var vm: AuthViewModel
	@Inject lateinit var factory: ViewModelProvider.Factory
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		binding = inflater.bind(R.layout.fragment_more_info, container)
		return binding.root
	}
	
	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		vm = getViewModel(factory)
		binding.vm = vm
		binding.handler = this
		binding.setLifecycleOwner(viewLifecycleOwner)
		vm.selectedExperiencePosition.observe(viewLifecycleOwner, Observer {
			vm.setExperience(it)
		})
		
		vm.selectedPreferencePosition.observe(viewLifecycleOwner, Observer {
			vm.setPreference(it)
		})
		
		vm.selectedPosPosition.observe(viewLifecycleOwner, Observer {
			vm.setPosition(it)
		})
		
		vm.selectedHeightPosition.observe(viewLifecycleOwner, Observer {
			vm.setHeight(it)
		})
		
		vm.selectedDistancePosition.observe(viewLifecycleOwner, Observer {
			vm.setDistance(it)
		})
	}
	
	fun addTopFinish() {
		val inflater = LayoutInflater.from(context)
		val binding: TopFinishesBinding = inflater.bind(R.layout.item_top_finishes, this.binding.topFinishesLL)
		binding.handler = this
		binding.vm = vm
		this.binding.topFinishesLL.addView(binding.root)
		this.binding.topFinishesLL.invalidate()
		binding.setLifecycleOwner(viewLifecycleOwner)
		vm.addTopFinish()
	}
	
	fun removeTopFinish(view: View) {
		binding.topFinishesLL.removeView(view)
		vm.removeTopFinish()
	}
	
	fun readTopFinishes() {
		val topFinishes = ArrayList<String>()
		binding.topFinishesLL.forEach {
			val topFinish = it.findViewById<AppCompatEditText>(R.id.topFinishesAET).text.toString()
			topFinishes.add(topFinish)
			vm.setTopFinishes(topFinishes)
		}
	}
	
	fun getItemView() : Int = R.layout.simple_spinner_item_1line
}

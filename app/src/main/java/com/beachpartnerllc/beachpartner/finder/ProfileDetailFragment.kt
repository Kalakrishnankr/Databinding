package com.beachpartnerllc.beachpartner.finder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.ProfileDetailFragmentBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.user.Profile
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 01 Oct 2018 at 11:54 AM
 */
class ProfileDetailFragment : BaseFragment() {
	@Inject lateinit var factory: ViewModelProvider.Factory
	private lateinit var binding: ProfileDetailFragmentBinding
	private lateinit var vm: FinderViewModel
	private var userId: Int = 0
	private var profile: Profile? = null
	
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_finder_card_detail, container, false)
		binding.handler = this
		userId = ProfileDetailFragmentArgs.fromBundle(arguments).id
		return binding.root
	}
	
	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		vm = getViewModel(factory)
		binding.vm = vm
		binding.setLifecycleOwner(viewLifecycleOwner)
		vm.getProfile(userId).observe(viewLifecycleOwner, Observer {
			if (it.isSuccess()) {
				vm.itemProfile = it.data!!
				Glide.with(this).load(vm.itemProfile!!.imageurl).apply(RequestOptions.placeholderOf(R.drawable
					.default_icon))
					.into(binding.avatarIMV)
			}
		})
	}
	
	fun backStack() {
		getFragmentManager()!!.popBackStack()
	}
	
}
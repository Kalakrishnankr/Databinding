package com.beachpartnerllc.beachpartner.connection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.ConnectionBinding
import com.beachpartnerllc.beachpartner.databinding.ConnectionItemBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseAdapter
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.base.BaseViewHolder
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.user.profile.Profile
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 26 Sep 2018 at 4:16 PM
 */
class ConnectionFragment : BaseFragment() {
	@Inject lateinit var factory: ViewModelProvider.Factory
	private lateinit var binding: ConnectionBinding
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = inflater.bind(R.layout.fragment_connection, container)
		return binding.root
	}
	
	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		
		val vm: ConnectionViewModel = getViewModel(factory)
		binding.vm = vm
		binding.setLifecycleOwner(viewLifecycleOwner)
		vm.getConnections().observe(viewLifecycleOwner, Observer {
			if (it.isSuccess()) {
				binding.adapter = BaseAdapter(it.data!!, R.layout.item_connection, ::ConnectionViewHolder)
			}
		})
	}
	
	class ConnectionViewHolder(itemBinding: ConnectionItemBinding)
		: BaseViewHolder<Profile, ConnectionItemBinding>(itemBinding) {
		init {
			itemBinding.knobIV.setOnClickListener {
				itemBinding.isOpen = !(itemBinding.isOpen ?: false)
			}
		}
		
		override fun bindTo(item: Profile) {
			itemBinding.item = item
		}
	}
}
package com.beachpartnerllc.beachpartner.connection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.ConnectionsItemBinding
import com.beachpartnerllc.beachpartner.databinding.InviteConnectionBinding
import com.beachpartnerllc.beachpartner.databinding.PotentialItemBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseAdapter
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.base.BaseViewHolder
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.user.Profile
import javax.inject.Inject


/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 21 Sep 2018 at 3:28 PM
 */
class InviteConnectionFragment : BaseFragment() {
	@Inject lateinit var factory: ViewModelProvider.Factory
	private lateinit var binding: InviteConnectionBinding
	private lateinit var vm: ConnectionViewModel
	private lateinit var potentialAdapter: BaseAdapter<Profile, PotentialItemBinding, PotentialViewHolder>
	private lateinit var connectionsAdapter: BaseAdapter<Profile, ConnectionsItemBinding, ConnectionsViewHolder>
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = inflater.bind(R.layout.fragment_invite_connection, container)
		binding.handler = this
		return binding.root
	}
	
	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		vm = getViewModel(factory)
		binding.vm = vm
		binding.setLifecycleOwner(viewLifecycleOwner)
		
		potentialAdapter = BaseAdapter(ArrayList(), R.layout.item_potential_partrner, ::PotentialViewHolder)
		binding.potentialAdapter = potentialAdapter
		
		vm.getConnections().observe(viewLifecycleOwner, Observer {
			if (it.isSuccess()) {
				connectionsAdapter = BaseAdapter(it.data!!, R.layout.item_connections, ::ConnectionsViewHolder)
				binding.connectionsAdapter = connectionsAdapter
			}
		})
	}
	
	fun onInvite() {
		vm.invite(potentialAdapter.items.map { it.userId }).observe(this, Observer { })
	}
	
	inner class ConnectionsViewHolder(itemBinding: ConnectionsItemBinding) :
		BaseViewHolder<Profile, ConnectionsItemBinding>(itemBinding) {
		init {
			itemBinding.addIV.setOnClickListener {
				potentialAdapter.addItem(connectionsAdapter.removeItem(adapterPosition))
				binding.validate = true
			}
		}
		
		override fun bindTo(item: Profile) {
			itemBinding.item = item
		}
	}
	
	inner class PotentialViewHolder(itemBinding: PotentialItemBinding) :
		BaseViewHolder<Profile, PotentialItemBinding>(itemBinding) {
		init {
			itemBinding.removeIV.setOnClickListener {
				connectionsAdapter.addItem(potentialAdapter.removeItem(adapterPosition))
				binding.validate = true
			}
		}
		
		override fun bindTo(item: Profile) {
			itemBinding.item = item
		}
	}
}

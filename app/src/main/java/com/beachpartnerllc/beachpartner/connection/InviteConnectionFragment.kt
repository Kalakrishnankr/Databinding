package com.beachpartnerllc.beachpartner.connection

import android.os.Bundle
import android.view.*
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
import com.beachpartnerllc.beachpartner.home.HomeActivity
import com.beachpartnerllc.beachpartner.user.profile.Profile
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.sothree.slidinguppanel.SlidingUpPanelLayout.*
import kotlinx.android.synthetic.main.activity_home.*
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
    private var connectionsAdapter: BaseAdapter<Profile, ConnectionsItemBinding, ConnectionsViewHolder>? = null
    private var connections: ArrayList<Profile>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflater.bind(R.layout.fragment_invite_connection, container)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = getViewModel(factory)
        binding.vm = vm
        binding.setLifecycleOwner(viewLifecycleOwner)

        potentialAdapter = BaseAdapter(ArrayList(), R.layout.item_potential_partrner, ::PotentialViewHolder)
        binding.potentialAdapter = potentialAdapter

        vm.connections.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess()) {
                connections = it.data!!.toMutableList() as ArrayList<Profile>
                connectionsAdapter = BaseAdapter(
                    connections!!,
                    R.layout.item_connections,
                    ::ConnectionsViewHolder)
                binding.connectionsAdapter = connectionsAdapter
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_search, menu)
        val item = menu!!.findItem(R.id.action_search)
        val searchView = (activity as HomeActivity).searchMSV
        searchView.setMenuItem(item)
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterConnections(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterConnections(newText)
                return true
            }
        })
    }

    private fun filterConnections(query: String?) {
        connectionsAdapter?.updateItems(
            connections
                ?.asSequence()
                ?.minus(potentialAdapter.items)
                ?.filter { it.fullName!!.contains(query ?: "", true) }
                ?.toMutableList(),
            { old, new -> old.userId == new.userId },
            { old, new -> old == new }
        )
    }

    override fun onDestroyView() {
        (activity as HomeActivity?)?.searchMSV?.closeSearch()
        super.onDestroyView()
    }

    inner class ConnectionsViewHolder(itemBinding: ConnectionsItemBinding) :
        BaseViewHolder<Profile, ConnectionsItemBinding>(itemBinding) {
        init {
            itemBinding.addIV.setOnClickListener {
                if (adapterPosition < 0) return@setOnClickListener

                potentialAdapter.addItem(connectionsAdapter!!.removeItem(adapterPosition))
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
                if (adapterPosition < 0) return@setOnClickListener

                connectionsAdapter!!.addItem(potentialAdapter.removeItem(adapterPosition))
                binding.sliderSPL.panelState = PanelState.ANCHORED
                binding.validate = true
            }
        }

        override fun bindTo(item: Profile) {
            itemBinding.item = item
        }
    }
}

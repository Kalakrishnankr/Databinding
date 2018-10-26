package com.beachpartnerllc.beachpartner.connection

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.ConnectionBinding
import com.beachpartnerllc.beachpartner.databinding.ConnectionItemBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseAdapter
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.base.BaseViewHolder
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.home.HomeActivity
import com.beachpartnerllc.beachpartner.user.profile.Profile
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 26 Sep 2018 at 4:16 PM
 */
class ConnectionFragment : BaseFragment() {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: ConnectionBinding
    private var adapter: BaseAdapter<Profile, ConnectionItemBinding, ConnectionViewHolder>? = null
    private var profiles: List<Profile>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflater.bind(R.layout.fragment_connection, container)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val vm: ConnectionViewModel = getViewModel(factory)
        binding.vm = vm
        binding.setLifecycleOwner(viewLifecycleOwner)
        vm.connections.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess()) {
                profiles = it.data!!
                adapter = BaseAdapter(profiles!!.toMutableList(), R.layout.item_connection, ::ConnectionViewHolder)
                binding.adapter = adapter
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_search, menu)
        val item = menu!!.findItem(R.id.action_search)
        val searchView = (activity as HomeActivity).searchMSV
        searchView.setMenuItem(item)
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.updateItems(
                    profiles?.filter { it.fullName!!.contains(newText!!, true) },
                    { old, new -> old.userId == new.userId },
                    { old, new -> old == new }
                )
                return false
            }
        })
    }

    override fun onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu()
        (activity as HomeActivity).searchMSV.closeSearch()
    }

    inner class ConnectionViewHolder(itemBinding: ConnectionItemBinding)
        : BaseViewHolder<Profile, ConnectionItemBinding>(itemBinding) {
        init {
            itemBinding.knobIV.setOnClickListener {
                itemBinding.isOpen = !(itemBinding.isOpen ?: false)
            }

            itemBinding.messageTV.setOnClickListener {
                with(adapter!!.items[adapterPosition]) {
                    val direction = ConnectionFragmentDirections.ActionMessage().setUserId(userId)
                    findNavController().navigate(direction)
                }
            }
        }

        override fun bindTo(item: Profile) {
            itemBinding.item = item
        }
    }
}
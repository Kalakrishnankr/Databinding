package com.beachpartnerllc.beachpartner.event


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.EventRequestBinding
import com.beachpartnerllc.beachpartner.databinding.EventRequestItemBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseAdapter
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.base.BaseViewHolder
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.home.AthleteHomeFragmentDirections
import javax.inject.Inject

class EventRequestFragment : BaseFragment() {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: EventRequestBinding
    private lateinit var adapter: BaseAdapter<Event, EventRequestItemBinding, ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflater.bind(R.layout.fragment_event_request, container)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val vm: EventViewModel = getViewModel(factory)
        binding.vm = vm
        binding.setLifecycleOwner(viewLifecycleOwner)
        vm.eventRequestList.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess()) {
                adapter = BaseAdapter(it.data!!, R.layout.item_event_request, ::ViewHolder)
                binding.adapter = adapter
            }
        })
    }

    inner class ViewHolder(itemBinding: EventRequestItemBinding) :
        BaseViewHolder<Event, EventRequestItemBinding>(itemBinding) {
        init {
            itemBinding.root.setOnClickListener {
                val eventId = adapter.items[adapterPosition].eventId
                val direction = AthleteHomeFragmentDirections.actionEvent(eventId)
                parentFragment?.findNavController()?.navigate(direction)
            }
        }

        override fun bindTo(item: Event) {
            itemBinding.item = item
        }
    }
}

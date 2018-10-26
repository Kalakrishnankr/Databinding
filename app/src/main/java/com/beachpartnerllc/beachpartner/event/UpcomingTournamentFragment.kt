package com.beachpartnerllc.beachpartner.event


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.UpcomingTournamentBinding
import com.beachpartnerllc.beachpartner.databinding.UpcomingTournamentItemBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseAdapter
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.base.BaseViewHolder
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import javax.inject.Inject

class UpcomingTournamentFragment : BaseFragment() {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: UpcomingTournamentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflater.bind(R.layout.fragment_upcoming_tournament, container)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val vm: EventViewModel = getViewModel(factory)
        vm.upcomingTournaments().observe(viewLifecycleOwner, Observer {
            if (it.isSuccess()) {
                binding.adapter = BaseAdapter(it.data!!, R.layout.item_upcoming_tournament, ::ViewHolder)
            }
        })
    }

    class ViewHolder(itemBinding: UpcomingTournamentItemBinding) :
        BaseViewHolder<Event, UpcomingTournamentItemBinding>(itemBinding) {
        override fun bindTo(item: Event) {
            itemBinding.item = item
        }
    }
}

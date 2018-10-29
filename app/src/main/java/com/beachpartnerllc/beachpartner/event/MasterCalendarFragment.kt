package com.beachpartnerllc.beachpartner.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.MasterCalendarBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 17 Sep 2018 at 4:38 PM
 */
class MasterCalendarFragment : BaseFragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: MasterCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflater.bind(R.layout.fragment_master_calendar, container)
        binding.adapter = EventAdapter(::onEvent)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val vm: EventViewModel = getViewModel(factory, false)
        binding.vm = vm
        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.masterCCV.setCurrentDate(vm.eventDate.value)
        vm.events.observe(viewLifecycleOwner, Observer { binding.adapter!!.submitList(it) })
    }

    private fun onEvent(event: Event) {
        val direction = CalendarFragmentDirections.ActionEvent(event.eventId)
        parentFragment?.findNavController()?.navigate(direction)
    }
}
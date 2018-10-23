package com.beachpartnerllc.beachpartner.finder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.SearchResultFragment2Binding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.user.profile.Profile
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 14 Sep 2018 at 2:18 PM
 */
class SearchResultFragment : BaseFragment() {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: SearchResultFragment2Binding
    private lateinit var vm: FinderViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflater.bind(R.layout.fragment_search_result, container)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = getViewModel(factory)
        binding.vm = vm
        binding.setLifecycleOwner(viewLifecycleOwner)
        vm.findProfiles().observe(viewLifecycleOwner, Observer {
            if (it.isSuccess()) {
                binding.loaderPB.isVisible = false
                val adapter = ProfileListingAdapter(context!!, ::alertFlagged, ::profileInfo)
                adapter.addAll(it.data!!)
                binding.profileCSV.setAdapter(adapter)
            }
        })
    }

    private fun alertFlagged(profile: Profile) {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("${getString(R.string.flag_title)} ${profile.firstName} ${profile.lastName} ?")
            .setMessage(R.string.flag_message)
            .setCancelable(true)
            .setPositiveButton(getString(R.string.yes)) { _, _ -> block(profile) }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    private fun block(profile: Profile) {
        vm.blockPerson(profile).observe(this, Observer { })
    }

    private fun profileInfo(profile: Profile) {
        val direction = SearchResultFragmentDirections.ActionProfileDetails(profile.userId)
        findNavController().navigate(direction)
    }
}

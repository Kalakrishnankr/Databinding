package com.beachpartnerllc.beachpartner.finder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.ProfileDeatailedInfoFragmentBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 01 Oct 2018 at 11:54 AM
 */
class ProfileDetailedInfoFragment : BaseFragment() {
	@Inject lateinit var factory: ViewModelProvider.Factory
	private lateinit var binding: ProfileDeatailedInfoFragmentBinding
	private lateinit var vm :FinderViewModel
	private  var userId : Int = 0
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_finder_card_detail,container,false)
		userId = ProfileDetailedInfoFragmentArgs.fromBundle(arguments).id
		return binding.root
	}
	
}
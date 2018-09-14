package com.beachpartnerllc.beachpartner.finder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.SearchFragmentBinding
import com.beachpartnerllc.beachpartner.etc.common.bind

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 11 Sep 2018 at 1:01 PM
 */
class SearchFragment : Fragment()  {
	
	private lateinit var mBinding: SearchFragmentBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
	}
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		mBinding = container!!.bind(R.layout.fragment_finder)
		return mBinding.root
		
	}
	
	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
	}
	
}
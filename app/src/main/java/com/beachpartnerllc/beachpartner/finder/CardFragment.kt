package com.beachpartnerllc.beachpartner.finder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.beachpartnerllc.beachpartner.R

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 14 Sep 2018 at 2:18 PM
 */
class CardFragment : Fragment() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		val view: View = inflater.inflate(R.layout.fragment_card,container,false)
		return view
	}
}
package com.beachpartnerllc.beachpartner.connection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.beachpartnerllc.beachpartner.etc.common.bind

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 21 Sep 2018 at 3:28 PM
 */
class ConnectionsFragment : Fragment() {
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val binding: ConnectionsBinding = inflater.bind(R.layout.fragment_connections, container)
	}
}
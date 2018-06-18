package com.beachpartnerllc.beachpartner.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beachpartnerllc.beachpartner.R

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 16 Jun 2018 at 8:01 PM
 */
class Home1Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_1, container, false)
    }
}
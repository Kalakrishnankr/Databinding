package com.beachpartnerllc.beachpartner.user.auth

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.SignUpFragment2Binding
import com.beachpartnerllc.beachpartner.etc.common.BaseFragment

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Jun 2018 at 12:47 PM
 */
class SignUp2Fragment : BaseFragment() {
    private lateinit var mBinding: SignUpFragment2Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_2, container, false)
        return mBinding.root
    }
}
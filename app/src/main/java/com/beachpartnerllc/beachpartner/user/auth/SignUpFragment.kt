package com.beachpartnerllc.beachpartner.user.auth

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.SignUpFragmentBinding
import com.beachpartnerllc.beachpartner.etc.common.BaseFragment

class SignUpFragment : BaseFragment() {
    private lateinit var mBinding: SignUpFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return mBinding.root
    }
}
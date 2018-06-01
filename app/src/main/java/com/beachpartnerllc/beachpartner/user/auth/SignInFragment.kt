package com.beachpartnerllc.beachpartner.user.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beachpartnerllc.beachpartner.databinding.SignInFragmentBinding
import com.beachpartnerllc.beachpartner.etc.common.BaseFragment

class SignInFragment: BaseFragment() {
    private lateinit var mBinding: SignInFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = SignInFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }
}

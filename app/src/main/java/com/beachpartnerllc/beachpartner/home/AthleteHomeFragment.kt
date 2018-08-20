package com.beachpartnerllc.beachpartner.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.AthleteHomeFragmentBinding
import com.beachpartnerllc.beachpartner.databinding.AvatarItemBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseAdapter
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 13 Jun 2018 at 4:13 PM
 */
class AthleteHomeFragment : BaseFragment() {
    private lateinit var mBinding: AthleteHomeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_athlete_home, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding.avatarAdapter = BaseAdapter<Avatar, AvatarItemBinding>(
                arrayListOf(Avatar(), Avatar()),
                R.layout.item_avatar,
                { binding, item -> binding.item = item }
        )
    }
}
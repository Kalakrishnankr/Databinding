package com.beachpartnerllc.beachpartner.user.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.PostRegistrationBinding
import com.beachpartnerllc.beachpartner.databinding.UnreceivedEmailBinding
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.etc.di.Injectable
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 12 Sep 2018 at 12:29 PM
 */
class PostRegistrationFragment : DialogFragment(), Injectable {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var vm: AuthViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vm = getViewModel(factory)

        val layoutRes = arguments?.getInt(ARG_LAYOUT_RES_ID) ?: R.layout.fragment_post_registration
        return if (layoutRes == R.layout.fragment_email_unreceived) {
            val binding: UnreceivedEmailBinding = inflater.bind(layoutRes, container)
            binding.handler = this
            binding.root
        } else {
            val binding: PostRegistrationBinding = inflater.bind(layoutRes, container)
            binding.handler = this
            binding.root
        }
    }

    fun onStateChanged(state: AuthState) {
        vm.state.value = state
        dismiss()
    }

    companion object {
        private const val ARG_LAYOUT_RES_ID = "arg_layout_res_id"

        fun newInstance(never: Boolean = true): PostRegistrationFragment {
            val fragment = PostRegistrationFragment()
            val resId = if (never) R.layout.fragment_email_unreceived else R.layout.fragment_post_registration
            fragment.arguments = bundleOf(ARG_LAYOUT_RES_ID to resId)
            return fragment
        }
    }
}


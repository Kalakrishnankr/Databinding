package com.beachpartnerllc.beachpartner.user.auth

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.SignUpFragmentBinding
import com.beachpartnerllc.beachpartner.etc.common.BaseFragment
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class SignUpFragment : BaseFragment() {
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: AuthViewModel
    private lateinit var mBinding: SignUpFragmentBinding
    private lateinit var mDisposable: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mViewModel = ViewModelProviders.of(activity!!, mFactory)[AuthViewModel::class.java]
        mBinding.vm = mViewModel
        mBinding.setLifecycleOwner(viewLifecycleOwner)

        mViewModel.selectedStatePosition.observe(this, Observer {
            it?.let { mViewModel.setState(it) }
        })

        val firstName = RxTextView.afterTextChangeEvents(mBinding.firstNameET).skip(mViewModel.signUpSkipInitCount())
        val lastName = RxTextView.afterTextChangeEvents(mBinding.lastNameET).skip(mViewModel.signUpSkipInitCount())
        val gender = RxRadioGroup.checkedChanges(mBinding.genderRG).skip(mViewModel.signUpSkipInitCount())
        val userType = RxRadioGroup.checkedChanges(mBinding.userTypeRG).skip(mViewModel.signUpSkipInitCount())
        val observables = listOf(firstName, lastName, gender, userType)
        mDisposable = Observable.combineLatest(observables, { readInput(it); mViewModel.signUpValidate.value = true })
                .doOnError { Timber.e(it) }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    private fun readInput(inputs: Array<out Any?>) {
        val profile = mViewModel.profile.value!!
        profile.gender = mBinding.genderRG.findViewById<RadioButton>(inputs[2].toString().toInt()).text.toString()
        profile.userType = mBinding.userTypeRG.findViewById<RadioButton>(inputs[3].toString().toInt()).text.toString()
    }

    override fun onDestroyView() {
        mDisposable.dispose()
        super.onDestroyView()
    }
}
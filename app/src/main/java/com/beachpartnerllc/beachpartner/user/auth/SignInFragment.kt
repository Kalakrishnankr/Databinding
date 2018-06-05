package com.beachpartnerllc.beachpartner.user.auth

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.SignInFragmentBinding
import com.beachpartnerllc.beachpartner.etc.common.BaseFragment
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class SignInFragment : BaseFragment() {
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mBinding: SignInFragmentBinding
    private lateinit var mDisposable: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val vm = ViewModelProviders.of(activity!!, mFactory)[AuthViewModel::class.java]
        mBinding.vm = vm
        mBinding.setLifecycleOwner(getViewLifeCycleOwner())

        val email = RxTextView.afterTextChangeEvents(mBinding.emailET).skip(vm.skipInitCount())
        val password = RxTextView.afterTextChangeEvents(mBinding.passwordET).skip(vm.skipInitCount())
        mDisposable = Observable.combineLatest(listOf(email, password), { vm.validate.value = true })
                .doOnError { Timber.e(it) }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    override fun onDestroyView() {
        mDisposable.dispose()
        super.onDestroyView()
    }
}

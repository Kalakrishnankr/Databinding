package com.beachpartnerllc.beachpartner.user.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.SignInFragmentBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class SignInFragment : BaseFragment() {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: SignInFragmentBinding
    private lateinit var vm: AuthViewModel
    private lateinit var disposable: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = getViewModel(factory)
        binding.vm = vm
        binding.handler = this
        binding.setLifecycleOwner(viewLifecycleOwner)

        val email = RxTextView.afterTextChangeEvents(binding.emailET).skip(vm.signInSkipInitCount())
        val password = RxTextView.afterTextChangeEvents(binding.passwordET).skip(vm.signInSkipInitCount())
        disposable = Observable.combineLatest(listOf(email, password)) { vm.signInValidate.value = true }
                .doOnError { Timber.e(it) }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun onSignIn() {
        vm.signIn().observe(this, Observer { })
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }
}

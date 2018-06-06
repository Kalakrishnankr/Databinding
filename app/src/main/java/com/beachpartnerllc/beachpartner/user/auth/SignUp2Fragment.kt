package com.beachpartnerllc.beachpartner.user.auth

import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.SignUpFragment2Binding
import com.beachpartnerllc.beachpartner.etc.common.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.*
import javax.inject.Inject


/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Jun 2018 at 12:47 PM
 */
class SignUp2Fragment : BaseFragment() {
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mBinding: SignUpFragment2Binding
    private lateinit var mDisposable: Disposable
    private lateinit var vm: AuthViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_2, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vm = getViewModel(mFactory)
        mBinding.vm = vm
        mBinding.handler = this
        mBinding.setLifecycleOwner(viewLifecycleOwner)

        val email = RxTextView.afterTextChangeEvents(mBinding.emailET).skip(vm.signUp2SkipInitCount())
        val mobile = RxTextView.afterTextChangeEvents(mBinding.mobileET).skip(vm.signUp2SkipInitCount())
        val password = RxTextView.afterTextChangeEvents(mBinding.passwordET).skip(vm.signUp2SkipInitCount())
        val dob = RxTextView.afterTextChangeEvents(mBinding.dobET).skip(vm.signUp2SkipInitCount())
        val observables = listOf(email, mobile, password, dob)
        mDisposable = Observable.combineLatest(observables, { vm.signUp2Validate.value = true })
                .doOnError { Timber.e(it) }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun onDobPicker() {
        val profile = vm.profile.value!!
        val cal = Calendar.getInstance()
        val dp = DatePicker(context, null, R.style.DatePickerStyle)
        dp.calendarViewShown = false
        dp.maxDate = cal.timeInMillis - if (profile.isAthlete()) 157784630000 else 568024668000 // 5 years or 18 years
        dp.init(cal[Calendar.YEAR], cal[Calendar.MONTH], cal[Calendar.DAY_OF_MONTH], null)
        val dialog = AlertDialog.Builder(context!!)
                .setCustomTitle(layoutInflater.inflate(R.layout.dialog_title, null, false))
                .setPositiveButton(R.string.okay, { _, _ -> mBinding.dobET.setText(getString(R.string.format_dob, dp.dayOfMonth, dp.month + 1, dp.year)) })
                .setNegativeButton(R.string.cancel, null)
                .create()
        dialog.setView(dp)
        dialog.show()
    }

    override fun onDestroyView() {
        mDisposable.dispose()
        super.onDestroyView()
    }
}
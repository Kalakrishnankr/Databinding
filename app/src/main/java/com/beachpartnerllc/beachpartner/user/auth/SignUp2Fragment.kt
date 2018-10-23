package com.beachpartnerllc.beachpartner.user.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.SignUpFragment2Binding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
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
	private lateinit var binding: SignUpFragment2Binding
	private lateinit var mDisposable: Disposable
	private lateinit var vm: AuthViewModel
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_2, container, false)
		return binding.root
	}
	
	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		
		vm = getViewModel(mFactory)
		binding.vm = vm
		binding.handler = this
		binding.setLifecycleOwner(viewLifecycleOwner)
		
		val email = RxTextView.afterTextChangeEvents(binding.emailET).skip(vm.signUp2SkipInitCount())
		val mobile = RxTextView.afterTextChangeEvents(binding.mobileET).skip(vm.signUp2SkipInitCount())
		val password = RxTextView.afterTextChangeEvents(binding.passwordET).skip(vm.signUp2SkipInitCount())
		val dob = RxTextView.afterTextChangeEvents(binding.dobET).skip(vm.signUp2SkipInitCount())
		val observables = listOf(email, mobile, password, dob)
		mDisposable = Observable.combineLatest(observables) { vm.signUp2Validate.value = true }
			.doOnError { Timber.e(it) }
			.subscribeOn(AndroidSchedulers.mainThread())
			.subscribe()
	}
	
	fun onDobPicker() {
		val profile = vm.profile.value!!
		val cal = Calendar.getInstance()
		if (profile.isDobValid()) cal.time = profile.dateOfBirth!!
		val dp = DatePicker(context, null, R.style.DatePickerStyle)
		dp.calendarViewShown = false
		dp.maxDate = Calendar.getInstance().timeInMillis - if (profile.isAthlete()) 157784630000 else 568024668000 // 5 years or 18 years
		val currentText = binding.dobET.text.toString()
		dp.init(cal[Calendar.YEAR], cal[Calendar.MONTH], cal[Calendar.DAY_OF_MONTH], null)

		val dialog = AlertDialog.Builder(context!!)
			.setCustomTitle(layoutInflater.inflate(R.layout.dialog_title, null, false))
			.setPositiveButton(R.string.okay) { _, _ ->
				binding.dobET.setText(getString(R.string.format_dob, dp.month + 1, dp.dayOfMonth, dp.year))
			}
			.setNegativeButton(R.string.cancel) { _, _ -> binding.dobET.setText(currentText) }
			.create()
		dialog.setView(dp)
		dialog.show()
	}
	
	fun register() {
		vm.register().observe(this, Observer { })
	}
	
	override fun onDestroyView() {
		mDisposable.dispose()
		super.onDestroyView()
	}
}
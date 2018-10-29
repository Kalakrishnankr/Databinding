package com.beachpartnerllc.beachpartner.user.profile


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.BasicInfoBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.etc.common.isMobile
import com.beachpartnerllc.beachpartner.etc.common.isName
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.user.auth.AuthViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject


class BasicInfoFragment : BaseFragment() {
	@Inject lateinit var factory: ViewModelProvider.Factory
	private lateinit var binding: BasicInfoBinding
	private lateinit var vm: AuthViewModel
	private lateinit var disposable: Disposable
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = inflater.bind(R.layout.fragment_basic_info, container)
		return binding.root
	}
	
	@SuppressLint("CheckResult")
	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		
		vm = getViewModel(factory)
		binding.vm = vm
		binding.setLifecycleOwner(viewLifecycleOwner)
		binding.adapter = ArrayAdapter(context, R.layout.simple_spinner_item_1line, resources.getStringArray(R.array.gender))
		binding.handler = this
		vm.getStates().observe(viewLifecycleOwner, Observer { it ->
			if (it.isSuccess()) {
				binding.spinnerState.adapter = ArrayAdapter(
					context!!,
					R.layout.simple_spinner_item_1line,
					it.data?.map { it.stateName }!!)
			}
		})
		
		val firstName = RxTextView.afterTextChangeEvents(binding.editFname)
			.map { it.editable().toString() }
			.map { it ->
				when {
					it.isEmpty() -> getString(R.string.field_empty_error)
					!it.isName() -> getString(R.string.invalid_name)
					else -> ""
				}
			}
		firstName.subscribe { it -> binding.firstNameError = if (it.isNotEmpty()) it else null }
		
		val lastName = RxTextView.afterTextChangeEvents(binding.editLname)
			.map { it.editable()?.toString() }
			.map {
				when {it.isEmpty() -> getString(R.string.field_empty_error)
					!it.isName() -> getString(R.string.invalid_name)
					else -> ""
				}
			}
		lastName.subscribe { it -> binding.lastNameError = if (it.isNotEmpty()) it else null }
		
		val mobile = RxTextView.afterTextChangeEvents(binding.editMobile)
			.map { it.editable().toString() }
			.map {
				when {it.isEmpty() -> getString(R.string.field_empty_error)
					!it.isMobile() -> getString(R.string.invalid_mobile)
					else -> ""
				}
			}
		mobile.subscribe { it -> binding.mobileError = if (it.isNotEmpty()) it else null }
		
		val observables = listOf(firstName, lastName, mobile)
		
		disposable = Observable.combineLatest(observables) { validate(it) }
			.subscribeOn(AndroidSchedulers.mainThread())
			.subscribe { vm.profileValidate.value = it as Boolean? }
	}
	
	private fun validate(inputs: Array<out Any>): Any {
		return inputs[0].toString().isEmpty() && inputs[1].toString().isEmpty() && inputs[2].toString().isEmpty()
	}
	
	fun onDobClick() {
		val profile = vm.profile.value!!
		val cal = Calendar.getInstance()
		if (profile.isDobValid()) cal.time = profile.dateOfBirth!!
		val dp = DatePicker(context, null, R.style.DatePickerStyle)
		dp.calendarViewShown = false
		dp.maxDate = Calendar.getInstance().timeInMillis - if (profile.isAthlete()) 157784630000 else 568024668000 // 5 years or 18 years
		val currentText = binding.editDob.text.toString()
		dp.init(cal[Calendar.YEAR], cal[Calendar.MONTH], cal[Calendar.DAY_OF_MONTH]) { _, year: Int, month: Int, day: Int ->
			binding.editDob.setText(getString(R.string.format_dob, month + 1, day, year))
		}
		
		val dialog = AlertDialog.Builder(context!!)
			.setCustomTitle(layoutInflater.inflate(R.layout.dialog_title, null, false))
			.setPositiveButton(R.string.okay, null)
			.setNegativeButton(R.string.cancel) { _, _ -> binding.editDob.setText(currentText) }
			.create()
		dialog.setView(dp)
		dialog.show()
	}
	
	override fun onDestroy() {
		disposable.dispose()
		super.onDestroy()
	}
}

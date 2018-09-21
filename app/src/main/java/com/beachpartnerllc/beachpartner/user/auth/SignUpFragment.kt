package com.beachpartnerllc.beachpartner.user.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.SignUpFragmentBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.user.profile.Gender
import com.beachpartnerllc.beachpartner.user.profile.UserType
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class SignUpFragment : BaseFragment() {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var vm: AuthViewModel
    private lateinit var binding: SignUpFragmentBinding
    private lateinit var disposable: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vm = ViewModelProviders.of(activity!!, factory)[AuthViewModel::class.java]
        binding.vm = vm
        binding.setLifecycleOwner(viewLifecycleOwner)

        vm.selectedStatePosition.observe(this, Observer {
            it?.let { vm.setStatePosition(it) }
        })

        vm.getStates().observe(viewLifecycleOwner, Observer { it ->
            if (it.isSuccess()) {
                binding.stateACS.adapter = ArrayAdapter(
                        context!!,
                        android.R.layout.simple_dropdown_item_1line,
                        it.data?.map { it.stateName }!!)
            }
        })

        val firstName = RxTextView.afterTextChangeEvents(binding.firstNameET).skip(vm.signUpSkipInitCount())
        val lastName = RxTextView.afterTextChangeEvents(binding.lastNameET).skip(vm.signUpSkipInitCount())
        val gender = RxRadioGroup.checkedChanges(binding.genderRG).skip(vm.signUpSkipInitCount())
        val userType = RxRadioGroup.checkedChanges(binding.userTypeRG).skip(vm.signUpSkipInitCount())
        val observables = listOf(firstName, lastName, gender, userType)
        disposable = Observable.combineLatest(observables) { readInput(it); vm.signUpValidate.value = true }
                .doOnError { Timber.e(it) }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    private fun readInput(inputs: Array<out Any?>) {
        val profile = vm.profile.value!!
        val gender = binding.genderRG.findViewById<RadioButton>(inputs[2].toString().toInt()).tag as Gender
        val userType = binding.userTypeRG.findViewById<RadioButton>(inputs[3].toString().toInt()).tag as UserType
        if (userType != profile.userType) profile.dob = null
        profile.gender = gender
        profile.userType = userType
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }
}

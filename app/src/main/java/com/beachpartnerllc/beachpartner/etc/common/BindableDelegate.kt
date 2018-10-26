package com.beachpartnerllc.beachpartner.etc.common

import androidx.databinding.BaseObservable
import kotlin.reflect.KProperty

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 10 Oct 2018 at 5:45 PM
 */
class BindableDelegate<in R : BaseObservable, T : Any?>(
	private var value: T?,
	private val bindingId: Int) {
	operator fun getValue(receiver: R, property: KProperty<*>): T? = value
	
	operator fun setValue(receiver: R, property: KProperty<*>, value: T?) {
		this.value = value
		receiver.notifyPropertyChanged(bindingId)
	}
}

fun <R : BaseObservable, T : Any> bindable(value: T?, bindingId: Int): BindableDelegate<R, T> =
	BindableDelegate(value, bindingId)

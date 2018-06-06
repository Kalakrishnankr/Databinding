package com.beachpartnerllc.beachpartner.etc.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 16 May 2018 at 6:05 PM
 */


/********         Arch Components         ***************/
inline fun <reified T : ViewModel> Fragment.getViewModel(factory: ViewModelProvider.Factory, isFromActivity: Boolean = true): T {
    return if (isFromActivity) ViewModelProviders.of(activity!!, factory)[T::class.java]
    else ViewModelProviders.of(this, factory)[T::class.java]
}

inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, factory)[T::class.java]
}


/********          View Components          **************/
inline fun <reified T : RecyclerView.ViewHolder> ViewGroup.create(createHolder: (View) -> T, @LayoutRes res: Int): T {
    val inflater = LayoutInflater.from(context)
    val view = inflater.inflate(res, this, false)
    return createHolder(view)
}


/********           System                   **************/
inline fun <reified T : AppCompatActivity> Context.startActivity() {
    startActivity(Intent(this, T::class.java))
}


/*******              String                 ***************/

fun String?.isEmail() = this != null && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String?.isMobile() = this != null && Patterns.PHONE.matcher(this).matches()

fun String?.isPassword() = this != null && length > 7

fun String?.isName() = this != null && this.matches("[a-zA-Z]+".toRegex())


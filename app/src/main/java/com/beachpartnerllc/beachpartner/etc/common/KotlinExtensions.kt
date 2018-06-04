package com.beachpartnerllc.beachpartner.etc.common

import android.content.Context
import android.content.Intent
import android.support.annotation.LayoutRes
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

inline fun <reified T : RecyclerView.ViewHolder> ViewGroup.create(createHolder: (View) -> T, @LayoutRes res: Int): T {
    val inflater = LayoutInflater.from(context)
    val view = inflater.inflate(res, this, false)
    return createHolder(view)
}

inline fun <reified T : AppCompatActivity> Context.startActivity() {
    startActivity(Intent(this, T::class.java))
}

fun String?.isEmail() = this != null && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String?.isPassword() = this != null && length > 7
package com.beachpartnerllc.beachpartner.utils

import android.view.MotionEvent
import android.view.View


/**
 * Created by seqato on 15/04/18.
 */

abstract class DoubleTapListener : View.OnTouchListener {
	
	internal var lastClickTime: Long = 0
	
	
	override fun onTouch(v: View, event: MotionEvent): Boolean {
		
		// long DOUBLE_CLICK_TIME_DELTA = 300;//milliseconds
		
		// long lastClickTime = 0;
		
		
		val clickTime = System.currentTimeMillis()
		if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
			onDoubleClick(v)
			lastClickTime = 0
		} else {
			onSingleClick(v)
		}
		lastClickTime = clickTime
		
		
		return false
		
	}
	
	
	abstract fun onSingleClick(v: View)
	abstract fun onDoubleClick(v: View): Boolean
	
	companion object {
		
		private val DOUBLE_CLICK_TIME_DELTA: Long = 300//milliseconds
	}
}
package com.beachpartnerllc.beachpartner.etc.common

import android.annotation.SuppressLint
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.format.DateFormat
import android.text.style.ForegroundColorSpan
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.beachpartnerllc.beachpartner.etc.common.OnCompoundDrawableClickListener.Companion.DRAWABLE_RIGHT
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.wang.avi.AVLoadingIndicatorView
import java.util.*


/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Jun 2018 at 9:44 AM
 */
@BindingAdapter("error")
fun setError(view: TextInputLayout, error: String?) {
	view.error = error
}

@BindingAdapter("error")
fun setError(view: TextInputLayout, error: Int) = setError(view, if (error == 0) null else view.resources.getString(error))

@BindingAdapter("isLoading")
fun setLoading(view: AVLoadingIndicatorView, isLoading: Boolean) {
	if (isLoading) view.smoothToShow()
	else view.smoothToHide()
}

@BindingAdapter("url", "isRound", requireAll = false)
fun setUrl(imageView: ImageView, url: String?, isRound: Boolean?) {
	GlideApp.with(imageView)
		.load(url)
		.diskCacheStrategy(DiskCacheStrategy.ALL)
		.apply(if (isRound == true) RequestOptions.circleCropTransform() else RequestOptions.centerCropTransform())
		.into(imageView)
}

@BindingAdapter("nestedScrollingEnabled")
fun setNestedScrollingEnabled(view: RecyclerView, nestedScrollingEnabled: Boolean) {
	view.isNestedScrollingEnabled = nestedScrollingEnabled
}

@BindingAdapter("foregroundColorSpan", "start", "end", requireAll = false)
fun setForegroundColorSpan(view: TextView, color: Int, start: Int = 0, end: Int = view.text.length - 1) {
	val spanBuilder = SpannableStringBuilder(view.text)
	spanBuilder.setSpan(ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
	view.text = spanBuilder
}

@BindingAdapter("onOkInSoftKeyboard")
fun setOnOkInSoftKeyboardListener(view: TextView, listener: OnOkInSoftKeyboardListener?) {
	if (listener == null) {
		view.setOnEditorActionListener(null)
	} else {
		view.setOnEditorActionListener { _, _, event ->
			if (event != null) {
				// if shift key is down, then we want to insert the '\n' char in the TextView;
				// otherwise, the default action is to invoke listener callback.
				if (!event.isShiftPressed) {
					listener.onOkInSoftKeyboard()
					return@setOnEditorActionListener false
				}
				return@setOnEditorActionListener false
			}
			
			listener.onOkInSoftKeyboard()
			return@setOnEditorActionListener false
		}
	}
}

@SuppressLint("ClickableViewAccessibility")
@BindingAdapter("onDrawableEndClick")
fun setOnDrawableEndClick(view: TextView, listener: OnCompoundDrawableClickListener?) {
	if (listener != null) {
		view.setOnTouchListener { _, event ->
			if (event.action == MotionEvent.ACTION_UP) {
				if (event.rawX >= (view.right - view.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
					listener.onDrawableEnd()
					return@setOnTouchListener true
				}
			}
			return@setOnTouchListener false
		}
	}
}

@BindingAdapter("spaceOffset")
fun setItemDecoration(view: RecyclerView, space: Float) {
	val layoutManager = view.layoutManager
	val spanCount = when (layoutManager) {
		is GridLayoutManager -> layoutManager.spanCount
		is StaggeredGridLayoutManager -> layoutManager.spanCount
		else -> 1
	}
	val decorator = ItemSpaceDecoration(space.toInt(), spanCount)
	view.addItemDecoration(decorator)
}

@BindingAdapter("setupWithViewPager")
fun setupWithViewPager(view: TabLayout, viewPager: ViewPager) {
	view.setupWithViewPager(viewPager)
}

@BindingAdapter("compactCalendarUseThreeLetterAbbreviation")
fun compactCalendarUseThreeLetterAbbreviation(view: CompactCalendarView, state: Boolean) {
	view.setUseThreeLetterAbbreviation(state)
}

@BindingAdapter("compactCalendarListener")
fun compactCalendarListener(view: CompactCalendarView, listener: DateListener) {
	view.setListener(object : CompactCalendarView.CompactCalendarViewListener {
		override fun onDayClick(dateClicked: Date) = listener.onDateChanged(dateClicked)
		
		override fun onMonthScroll(firstDayOfNewMonth: Date) = listener.onDateChanged(firstDayOfNewMonth)
	})
}

@BindingAdapter("android:text", "format", requireAll = true)
fun formatText(view: TextView, date: Date?, format: String) {
	date?.let { view.text = DateFormat.format(format, it) }
}

@BindingAdapter("shadowHeight")
fun setShadowHeight(view: SlidingUpPanelLayout, height: Int) {
	view.shadowHeight = height
}

@BindingAdapter("goneUntil")
fun goneUntil(view: View, isGone: Boolean) {
	view.visibility = if (isGone) View.GONE else View.VISIBLE
}

@BindingAdapter("scrollTo")
fun scrollTo(view: NestedScrollView, direction: Int) {
	view.fullScroll(direction)
}
package com.beachpartnerllc.beachpartner.etc.common

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beachpartnerllc.beachpartner.etc.common.OnCompoundDrawableClickListener.Companion.DRAWABLE_RIGHT
import com.google.android.material.textfield.TextInputLayout
import com.wang.avi.AVLoadingIndicatorView
import io.apptik.widget.MultiSlider
import timber.log.Timber


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

@BindingAdapter("url")
fun setUrl(imageView: ImageView, url: String?) {
    /*Picasso.with(imageView.context)
            .load(url)
            .fit()
            .centerCrop()
            .into(imageView)*/
}

@BindingAdapter("nestedScrollingEnabled")
fun setNestedScrollingEnabled(view: RecyclerView, nestedScrollingEnabled: Boolean) {
    view.isNestedScrollingEnabled = nestedScrollingEnabled
}

@BindingAdapter("foregroundColorSpan", "start", "end", requireAll = false)
fun setForegroundColorSpan(view: TextView, color: Int, start: Int = 0, end: Int = view.text.length - 1) {
    Timber.e("Text: %s", view.text)
    val spanBuilder = SpannableStringBuilder(view.text)
    spanBuilder.setSpan(ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    view.text = spanBuilder
}

/*@BindingAdapter("disableShift")
fun disableShiftMode(view: BottomNavigationView, disableShift: Boolean) {
    val menuView = view.getChildAt(0) as BottomNavigationMenuView
    try {
        val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
        shiftingMode.isAccessible = true
        shiftingMode.setBoolean(menuView, false)
        shiftingMode.isAccessible = false
        for (i in 0 until menuView.childCount) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView

            item.setShiftingMode(!disableShift)
            // set once again checked value, so view will be updated

            item.setChecked(item.itemData.isChecked)
        }
    } catch (e: NoSuchFieldException) {
        Log.e("BNVHelper", "Unable to get shift mode field", e)
    } catch (e: IllegalAccessException) {
        Log.e("BNVHelper", "Unable to change value of shift mode", e)
    }
}*/

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

@BindingAdapter("onThumbValueChange")
fun setOnThumbValueChangeListener(view: MultiSlider, listener: OnMinMaxValueListener) {
	view.setOnThumbValueChangeListener { multiSlider, thumb, thumbIndex, value ->
		//Timber.e(view.min.toString())
		if (thumbIndex ==0) listener.onMinMax(value,thumbIndex) else listener.onMinMax(value,thumbIndex)
	}
}

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

@BindingAdapter("itemDecoration", "offset", requireAll = false)
fun setItemDecoration(view: RecyclerView, decoration: String, offset: Int) {
    val decorator: RecyclerView.ItemDecoration? = when (decoration) {
        "ItemOffsetDecoration" -> ItemOffsetDecoration(view.context, offset)
        else -> null
    }
	view.addItemDecoration(decorator!!)
}
package com.beachpartnerllc.beachpartner.etc.common

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beachpartnerllc.beachpartner.etc.common.OnCompoundDrawableClickListener.Companion.DRAWABLE_RIGHT
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.material.textfield.TextInputLayout
import com.wang.avi.AVLoadingIndicatorView
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

@BindingAdapter("url", "placeHolder", requireAll = true)
fun setImageUrl(imageView: ImageView, url: String?, placeholder: Drawable) {
	Glide.with(imageView.context)
		.load(url)
		.apply(RequestOptions().circleCrop())
		.apply(RequestOptions.placeholderOf(placeholder))
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
fun setOnDrawableEndClick(editText: AppCompatEditText, listener: OnCompoundDrawableClickListener?) {
	val padding = 10
	if (listener != null) {
		editText.setOnTouchListener { _, event ->
			if (event.action == MotionEvent.ACTION_DOWN) {
				if (editText.compoundDrawables[DRAWABLE_RIGHT] == null) return@setOnTouchListener false
				else if (event.rawX >= (editText.right - editText.compoundDrawables[DRAWABLE_RIGHT].bounds.width() -
						padding)) {
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

@Suppress("UNCHECKED_CAST")
@BindingAdapter("itemView")
fun setItemView(view: Spinner, itemView: Int) {
	val adapter = view.adapter as ArrayAdapter<String>
	val items = ArrayList<String>(adapter.count)
	for (i in 0 until adapter.count) {
		items.add(adapter.getItem(i))
	}
	view.adapter = ArrayAdapter<String>(view.context, itemView, items)
}

@BindingAdapter("url", "listener", requireAll = false)
fun setUrl(view: PlayerView, url: Any?, listener: PlayerStateChangeListener) {
	val uri: Uri? = if (url is String) Uri.parse(url as String?) else url as Uri?
	val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(DefaultBandwidthMeter()))
	val player: SimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(view.context, trackSelector)
	val dataSourceFactory = DefaultDataSourceFactory(view.context, "ua")
	val mediaSource = ExtractorMediaSource(uri, dataSourceFactory, DefaultExtractorsFactory(), null, null)
	player.prepare(mediaSource)
	player.apply {
		volume = 0f
		repeatMode = Player.REPEAT_MODE_ONE
		playWhenReady = true
		videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
	}
	view.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL)
	view.player = player
	player.addListener(object : Player.DefaultEventListener() {
		override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
			super.onPlayerStateChanged(playWhenReady, playbackState)
			Timber.d("current state: $playbackState")
			listener.onPlayerStateChanged(playbackState)
		}
	})
}

@BindingAdapter("visibleIf")
fun changeVisibility(view: View, visible: Boolean) {
	if (visible) view.visibility = View.VISIBLE
	else view.visibility = View.GONE
}
package com.beachpartnerllc.beachpartner.etc.common

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.format.DateFormat
import android.text.style.ForegroundColorSpan
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.beachpartnerllc.beachpartner.etc.common.OnCompoundDrawableClickListener.Companion.DRAWABLE_RIGHT
import com.beachpartnerllc.beachpartner.finder.cardstackview.CardStackView
import com.beachpartnerllc.beachpartner.finder.cardstackview.SwipeDirection
import com.beachpartnerllc.beachpartner.utils.DoubleTapListener
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.github.sundeepk.compactcalendarview.CompactCalendarView
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
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.wang.avi.AVLoadingIndicatorView
import io.apptik.widget.MultiSlider
import timber.log.Timber
import java.util.*


/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Jun 2018 at 9:44 AM
 */

@BindingAdapter("error")
fun setError(view: TextInputLayout, error: String?) {
    view.error = error
}

@BindingAdapter("isLoading")
fun setLoading(view: AVLoadingIndicatorView, isLoading: Boolean) {
    if (isLoading) view.smoothToShow()
    else view.smoothToHide()
}

@BindingAdapter("imageUrl", "isRound", "placeHolder", requireAll = false)
fun setImageUrl(imageView: ImageView, url: String?, isRound: Boolean?, placeholder: Drawable?) {
    GlideApp.with(imageView)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply(if (isRound == true) RequestOptions.circleCropTransform() else RequestOptions.centerCropTransform())
        .apply(RequestOptions.placeholderOf(placeholder))
        .into(imageView)
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
    val padding = 10
    if (listener != null) {
        view.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (view.compoundDrawables[DRAWABLE_RIGHT] == null) return@setOnTouchListener false
                else if (event.rawX >= (view.right - view.compoundDrawables[DRAWABLE_RIGHT].bounds.width() -
                        padding)) {
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

@SuppressLint("ClickableViewAccessibility")
@BindingAdapter("onThumbValueChange")
fun setOnThumbValueChangeListener(view: MultiSlider, listener: OnMinMaxValueListener) {
    view.setOnThumbValueChangeListener { multiSlider, thumb, thumbIndex, value ->
        if (thumbIndex == 0) listener.onMinMax(value, thumbIndex)
        else listener.onMinMax(value, thumbIndex)
    }
}

@BindingAdapter("leftThumbValue")
fun leftThumbValue(view: MultiSlider, value: Int) {
    view.min = value
}

@BindingAdapter("rightThumbValue")
fun rightThumbValue(view: MultiSlider, value: Int) {
    view.max = value
}

@BindingAdapter("onCardEventChange")
fun setOnCardEventListener(view: CardStackView, listener: OnCardSwipeChangeListener) {
    view.setCardEventListener(object : CardStackView.CardEventListener {
        override fun onCardDragging(percentX: Float, percentY: Float) {
        }

        override fun onCardSwiped(direction: SwipeDirection?, index: Int) {
            listener.onDirection(direction!!, index)
        }

        override fun onCardReversed() {
        }

        override fun onCardMovedToOrigin() {
        }

        override fun onCardClicked(index: Int) {
        }
    })
}

@BindingAdapter("compactCalendarListener")
fun compactCalendarListener(view: CompactCalendarView, listener: DateListener) {
    view.setListener(object : CompactCalendarView.CompactCalendarViewListener {
        override fun onDayClick(dateClicked: Date) = listener.onDateChanged(dateClicked)

        override fun onMonthScroll(firstDayOfNewMonth: Date) = listener.onDateChanged(firstDayOfNewMonth)
    })
}

@BindingAdapter("setupWithViewPager")
fun setupWithViewPager(view: TabLayout, viewPager: ViewPager) {
    view.setupWithViewPager(viewPager)
}

@BindingAdapter("compactCalendarUseThreeLetterAbbreviation")
fun compactCalendarUseThreeLetterAbbreviation(view: CompactCalendarView, state: Boolean) {
    view.setUseThreeLetterAbbreviation(state)
}

@SuppressLint("ClickableViewAccessibility")
@BindingAdapter("onDoubleClickEvent")
fun setOnDoubleTapListener(view: ImageView, listener: OnImageDoubleClickListener) {
    view.setOnTouchListener(object : DoubleTapListener() {
        override fun onSingleClick(v: View) {
        }

        override fun onDoubleClick(v: View): Boolean {
            listener.imageDoubleTap()
            return true
        }
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

@Suppress("UNCHECKED_CAST")
@BindingAdapter("itemView")
fun setItemView(view: Spinner, itemView: Int) {
    val adapter = view.adapter as ArrayAdapter<String>
    val items = ArrayList<String>(adapter.count)
    for (i in 0 until adapter.count) {
        items.add(adapter.getItem(i)!!)
    }
    view.adapter = ArrayAdapter<String>(view.context, itemView, items)
}

@BindingAdapter("url", "listener", requireAll = false)
fun setImageUrl(view: PlayerView, url: Any?, listener: PlayerStateChangeListener) {
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

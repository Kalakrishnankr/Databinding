package com.beachpartnerllc.beachpartner.finder

import android.content.Context
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.ProfileItemBinding
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.user.profile.Profile
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.wang.avi.AVLoadingIndicatorView


/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 24 Sep 2018 at 10:17 AM
 */
class ProfileListingAdapter(
    context: Context,
    private val callback: (Profile) -> Unit,
    private val call: (Profile) -> Unit) : ArrayAdapter<Profile>(context, 0) {

    private lateinit var binding: ProfileItemBinding

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder = if (convertView == null) {
            binding = parent!!.bind(R.layout.item_profile)
            binding.adapter = this
            val holder = ViewHolder(binding)
            binding.root.tag = holder
            holder
        } else {
            convertView.tag as ViewHolder
        }

        binding.loadingAVLV.visibility = View.INVISIBLE
        binding.videoPV.visibility = View.INVISIBLE
        binding.avatarIV.visibility = View.VISIBLE
        return holder.bind(getItem(position)!!, context, callback, call)
    }

    class ViewHolder(private val binding: ProfileItemBinding) {
        fun bind(item: Profile, context: Context, callback: (Profile) -> Unit, call: (Profile) -> Unit): View {
            binding.profile = item
            binding.executePendingBindings()
            Glide.with(context)
                    .load(item.avatarUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.default_icon))
                    .into(binding.avatarIV)

            binding.flagIMV.setOnClickListener {
                callback.invoke(item)
            }
            binding.infoIMV.setOnClickListener {
                call.invoke(item)
            }
            return binding.root
        }
    }

    fun setVideoUrl(pgbar: AVLoadingIndicatorView, imageview: ImageView, exoPlayer: SimpleExoPlayerView, url: Any?) {
        pgbar.visibility = View.VISIBLE
        if (url == null) return
        val simpleExoplayer: SimpleExoPlayer
        val bandwidthMeter = DefaultBandwidthMeter()
        exoPlayer.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL)
        val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
        simpleExoplayer = ExoPlayerFactory.newSimpleInstance(exoPlayer.context, trackSelector)
        val uri: Uri? = if (url is String) Uri.parse(url as String?) else url as Uri?
        val mediaSource = buildMediaSource(uri, exoPlayer.context)
        simpleExoplayer.prepare(mediaSource)
        simpleExoplayer.volume = 0f
        simpleExoplayer.clearVideoSurface()
        simpleExoplayer.playWhenReady = true
        simpleExoplayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
        exoPlayer.player = simpleExoplayer
        exoPlayer.controllerAutoShow = false
        setStateChangeListener(pgbar, simpleExoplayer, imageview, exoPlayer)
    }

    private fun setStateChangeListener(
            loadingAVL: AVLoadingIndicatorView,
            simpleExoplayer: SimpleExoPlayer?,
            imageview: ImageView,
            exoPlayer: SimpleExoPlayerView) {
        simpleExoplayer!!.addListener(object : Player.DefaultEventListener() {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> {
                        loadingAVL.visibility = View.VISIBLE
                    }
                    Player.STATE_READY -> {
                        loadingAVL.visibility = View.INVISIBLE
                        imageview.visibility = View.INVISIBLE
                        exoPlayer.visibility = View.VISIBLE
                    }
                    Player.STATE_ENDED -> {
                        imageview.visibility = View.VISIBLE
                        exoPlayer.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun buildMediaSource(uri: Uri?, context: Context): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(context, "ua")
        return ExtractorMediaSource(uri, dataSourceFactory, DefaultExtractorsFactory(), null, null)
    }
}
package com.beachpartnerllc.beachpartner.user.profile

import android.Manifest
import android.app.Activity.*
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.AthleteProfileBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.user.auth.AuthViewModel
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
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import javax.inject.Inject


class AthleteProfileFragment : BaseFragment() {
	private lateinit var binding: AthleteProfileBinding
	@Inject lateinit var factory: ViewModelProvider.Factory
	private lateinit var vm: AuthViewModel
	
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = inflater.bind(R.layout.fragment_athlete_profile, container)
		binding.tabs.setupWithViewPager(binding.pager)
		val fragments = arrayListOf(BasicInfoFragment(), MoreInfoFragment())
		binding.adapter = ViewPagerAdapter(childFragmentManager, fragments,
			resources.getStringArray(R.array.profile_titles).asList())
		binding.handler = this
		return binding.root
	}
	
	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		vm = getViewModel(factory)
		binding.vm = vm
		vm.isProfileEdit.observe(viewLifecycleOwner, Observer {
				activity!!.invalidateOptionsMenu()
		})
		binding.setLifecycleOwner(viewLifecycleOwner)
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}
	
	fun fabClick() {
		binding.fabFAM.toggle(true)
	}
	
	fun shareImage() {
		if (binding.profilePicIV.drawable != null)
			Toast.makeText(context, "Sharing.....", Toast.LENGTH_LONG).show()
	}
	
	fun uploadImage() {
		if (!hasPermissions(context, permissions)) requestPermissions(permissions, 21)
		else startActivityForResult(pickImageIntent(), PICK_IMAGE_REQUEST)
	}
	
	fun uploadVideo() {
		if (!hasPermissions(context, permissions)) requestPermissions(permissions, 22)
		else startActivityForResult(pickVideoIntent(), PICK_VIDEO_REQUEST)
	}
	
	private fun hasPermissions(context: Context?, permissions: Array<String>): Boolean {
		return permissions.map { checkSelfPermission(context!!, it) != PackageManager.PERMISSION_GRANTED }.contains(false)
	}
	
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
			if (data!!.hasExtra("data")) {
				val img = data.extras!!.get("data") as Bitmap
				Glide.with(requireContext()).load(img).apply(RequestOptions.circleCropTransform())
					.into(binding.profilePicIV)
			} else
				Glide.with(requireContext()).load(data.data).apply(RequestOptions.circleCropTransform())
					.into(binding.profilePicIV)
			binding.progressBarProfileImg.visibility = View.GONE
		} else if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK) {
			val selectedVideoUri = data!!.data
			val dashUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"
			setVideoUrl(binding.exoplayerProfile, selectedVideoUri)
		}
	}
	
	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
		when (requestCode) {
			CAMERA_PERMISSION -> {
				if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
					startActivityForResult(pickImageIntent(), PICK_IMAGE_REQUEST)
				} else {
					Toast.makeText(context, getString(R.string.no_camera_permission), Toast.LENGTH_LONG).show()
				}
				return
			}
			VIDEO_PERMISSION -> {
				if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
					startActivityForResult(pickVideoIntent(), PICK_VIDEO_REQUEST)
				} else {
					Toast.makeText(context, getString(R.string.no_camera_permission), Toast.LENGTH_LONG).show()
				}
				return
			}
			else -> {
				return
			}
		}
	}
	
	private fun pickImageIntent(): Intent? {
		val imageIntent1 = Intent(Intent.ACTION_GET_CONTENT)
		imageIntent1.type = "image/*"
		val imageIntent2 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
		val intentList = arrayListOf(imageIntent1, imageIntent2)
		var chooserIntent: Intent? = null
		if (intentList.size > 0) {
			chooserIntent = Intent.createChooser(intentList.removeAt(intentList.size - 1), getString(R.string.max_size_image))
			chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toTypedArray<Parcelable>())
		}
		return chooserIntent
	}
	
	private fun pickVideoIntent(): Intent? {
		val videoIntent1 = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
		videoIntent1.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
		videoIntent1.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 10485760L)// 10*1024*1024 = 10MB  10485760L
		videoIntent1.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0)
		videoIntent1.putExtra(MediaStore.Video.Thumbnails.HEIGHT, 480)
		videoIntent1.putExtra(MediaStore.Video.Thumbnails.WIDTH, 720)
		val videoIntent2 = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI)
		videoIntent2.type = "video/*"
		val intentList = arrayListOf(videoIntent1, videoIntent2)
		var chooserIntent: Intent? = null
		if (intentList.size > 0) {
			chooserIntent = Intent.createChooser(intentList.removeAt(intentList.size - 1), getString(R.string.max_size_video))
			chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toTypedArray<Parcelable>())
		}
		return chooserIntent
	}
	
	private fun setVideoUrl(exoPlayer: PlayerView, url: Any?) {
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
		simpleExoplayer.repeatMode = Player.REPEAT_MODE_ONE
		simpleExoplayer.playWhenReady = true
		simpleExoplayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
		exoPlayer.player = simpleExoplayer
		setStateChangeListener(simpleExoplayer)
	}
	
	private fun buildMediaSource(uri: Uri?, context: Context): MediaSource {
		val dataSourceFactory = DefaultDataSourceFactory(context, "ua")
		return ExtractorMediaSource(uri, dataSourceFactory, DefaultExtractorsFactory(), null, null)
	}
	
	private fun setStateChangeListener(simpleExoPlayer: SimpleExoPlayer) {
		simpleExoPlayer.addListener(object : Player.DefaultEventListener() {
			override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
				super.onPlayerStateChanged(playWhenReady, playbackState)
				binding.vm!!.currentState.value = when (playbackState) {
					Player.STATE_IDLE -> true
					Player.STATE_BUFFERING -> true
					Player.STATE_READY -> false
					else -> true
				}
			}
		})
	}
	
	override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
		menu!!.clear()
		if (vm.isProfileEdit.value == true) inflater?.inflate(R.menu.menu_save_discard, menu)
		super.onCreateOptionsMenu(menu, inflater)
	}
	
	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		when(item!!.itemId){
			R.id.action_discard -> {
				vm.editable(false)
				return true
			}
			R.id.action_save ->{
				vm.editable(false)
				return true
			}
		}
		return super.onOptionsItemSelected(item)
	}
	
	companion object {
		private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
	}
}

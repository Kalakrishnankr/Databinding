package com.beachpartnerllc.beachpartner.etc.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Parcelable
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.etc.di.Injectable


/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 31 May 2018 at 1:50 PM
 */
abstract class BaseFragment : Fragment(), Injectable {
	val PICK_IMAGE_REQUEST = 0
	val PICK_VIDEO_REQUEST = 1
	val CAMERA_PERMISSION = 21
	val VIDEO_PERMISSION = 22
	
	fun pickImageIntent(): Intent? {
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
	
	fun pickVideoIntent(): Intent? {
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
}
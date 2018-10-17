package com.beachpartnerllc.beachpartner.etc.base

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Parcelable
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.etc.di.Injectable


/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 31 May 2018 at 1:50 PM
 */
abstract class BaseFragment : Fragment(), Injectable {

    fun hasPermissions(context: Context?, permissions: Array<String>): Boolean {
        return permissions.map { ActivityCompat.checkSelfPermission(context!!, it) != PackageManager.PERMISSION_GRANTED }.contains(false)
    }

    fun pickImageIntent(): Intent {
        val imageIntent1 = Intent(Intent.ACTION_GET_CONTENT)
        imageIntent1.type = "image/*"
        val imageIntent2 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val intentList = arrayListOf(imageIntent1, imageIntent2)
        val chooserIntent: Intent
        chooserIntent = Intent.createChooser(intentList.removeAt(intentList.size - 1), getString(R.string.max_size_image))
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toTypedArray<Parcelable>())
        return chooserIntent
    }

    fun pickVideoIntent(): Intent {
        val videoIntent1 = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        videoIntent1.apply {
            putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            putExtra(MediaStore.EXTRA_SIZE_LIMIT, 10485760L)// 10*1024*1024 = 10MB  10485760L
            putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0)
            putExtra(MediaStore.Video.Thumbnails.HEIGHT, 480)
            videoIntent1.putExtra(MediaStore.Video.Thumbnails.WIDTH, 720)
        }
        val videoIntent2 = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI)
        videoIntent2.type = "video/*"
        val intentList = arrayListOf(videoIntent1, videoIntent2)
        val chooserIntent: Intent
        chooserIntent = Intent.createChooser(intentList.removeAt(intentList.size - 1), getString(R.string.max_size_video))
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toTypedArray<Parcelable>())
        return chooserIntent
    }

    companion object {
        const val PICK_IMAGE_REQUEST = 0
        const val PICK_VIDEO_REQUEST = 1
        const val CAMERA_PERMISSION = 21
        const val VIDEO_PERMISSION = 22
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}
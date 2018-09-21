package com.beachpartnerllc.beachpartner.etc.base

import androidx.fragment.app.Fragment
import com.beachpartnerllc.beachpartner.etc.di.Injectable


/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 31 May 2018 at 1:50 PM
 */
abstract class BaseFragment : Fragment(), Injectable {
	val PICK_IMAGE_REQUEST = 0
	val PICK_VIDEO_REQUEST = 1
	val CAMERA_PERMISSION  = 21
	val VIDEO_PERMISSION   = 22
}
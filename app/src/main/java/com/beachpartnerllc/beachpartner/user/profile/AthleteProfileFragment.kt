package com.beachpartnerllc.beachpartner.user.profile

import android.app.Activity.*
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.amazonaws.mobile.client.AWSMobileClient
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.AthleteProfileBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.ImageFilePath
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.home.HomeActivity
import com.beachpartnerllc.beachpartner.user.auth.AuthViewModel
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
		vm.isProfileEdit.observe(viewLifecycleOwner, Observer { activity!!.invalidateOptionsMenu() })
		vm.profileValidate.observe(viewLifecycleOwner, Observer { activity!!.invalidateOptionsMenu() })
		vm.imageUploadProgress.observe(viewLifecycleOwner, Observer { })
		vm.videoUploadProgress.observe(viewLifecycleOwner, Observer { })
		binding.setLifecycleOwner(viewLifecycleOwner)
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
		AWSMobileClient.getInstance().initialize(context).execute()
	}
	
	fun fabClick() {
		binding.fabFAM.toggle(true)
	}
	
	fun shareImage() {
		if (binding.profilePicIV.drawable != null) {
			val uri = ImageFilePath.getImageUri(context, binding.profilePicIV.drawable.toBitmap())
			val intent = Intent(Intent.ACTION_SEND)
			intent.putExtra(Intent.EXTRA_TEXT, "https://www.beachpartner.com/preregistration/")
			intent.putExtra(Intent.EXTRA_SUBJECT, "BeachPartner App")
			intent.putExtra(Intent.EXTRA_STREAM, uri)
			intent.type = "image/*"
			startActivity(Intent.createChooser(intent, "Share image via..."))
		} else Toast.makeText(context, getString(R.string.share_image_error), Toast.LENGTH_LONG).show()
	}
	
	fun uploadImage() {
		if (!hasPermissions(context, permissions)) requestPermissions(permissions, 21)
		else startActivityForResult(pickImageIntent(), PICK_IMAGE_REQUEST)
	}
	
	fun uploadVideo() {
		if (!hasPermissions(context, permissions)) requestPermissions(permissions, 22)
		else startActivityForResult(pickVideoIntent(), PICK_VIDEO_REQUEST)
	}
	
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
			val extension: String?
			if (data!!.hasExtra("data")) {
				val img = data.extras!!.get("data") as Bitmap
				val uri = ImageFilePath.getImageUri(context, img)
				extension = ImageFilePath.getExtension(ImageFilePath.getPath(context, uri))
				vm.uploadImageToS3(ImageFilePath.getPath(context, uri), extension)
					.observe(viewLifecycleOwner, Observer { })
			} else {
				extension = ImageFilePath.getExtension(ImageFilePath.getPath(context, data.data))
				vm.uploadImageToS3(ImageFilePath.getPath(context, data.data), extension)
					.observe(viewLifecycleOwner, Observer { })
			}
		} else if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK) {
			val extension = ImageFilePath.getExtension(ImageFilePath.getPath(context, data!!.data))
			vm.uploadVideoToS3(ImageFilePath.getPath(context, data.data), extension)
				.observe(viewLifecycleOwner, Observer { })
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
	
	override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
		menu!!.clear()
		val homeActivity = activity as HomeActivity
		if (vm.isProfileEdit.value == true) {
			inflater?.inflate(R.menu.menu_save_discard, menu)
			homeActivity.supportActionBar!!.title = getString(R.string.edit_profile)
			menu.findItem(R.id.action_save).isEnabled = vm.profileValidate.value == true
		} else
			homeActivity.supportActionBar!!.title = getString(R.string.my_profile)
		
		super.onCreateOptionsMenu(menu, inflater)
	}
	
	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		when (item!!.itemId) {
			R.id.action_discard -> {
				vm.editable(false)
				return true
			}
			R.id.action_save -> {
				vm.editable(false)
				vm.isTopFinishesSet.value = true
				vm.updateAthlete().observe(this, Observer { })
				return true
			}
		}
		return super.onOptionsItemSelected(item)
	}
}

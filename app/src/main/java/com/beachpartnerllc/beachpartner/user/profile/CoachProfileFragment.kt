package com.beachpartnerllc.beachpartner.user.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.CoachProfileBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.ImageFilePath
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.home.HomeActivity
import com.beachpartnerllc.beachpartner.user.auth.AuthViewModel
import javax.inject.Inject

class CoachProfileFragment : BaseFragment() {
	private lateinit var binding: CoachProfileBinding
	@Inject lateinit var factory: ViewModelProvider.Factory
	private lateinit var vm: AuthViewModel
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = inflater.bind(R.layout.fragment_coach_profile, container)
		binding.tabs.setupWithViewPager(binding.pager)
		val fragments = arrayListOf(BasicInfoFragment(), CoachMoreInfoFragment())
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
		vm.profileValidate.observe(viewLifecycleOwner, Observer { activity!!.invalidateOptionsMenu() })
		binding.setLifecycleOwner(viewLifecycleOwner)
	}
	
	
	fun uploadImage() {
		if (!hasPermissions(context, permissions)) requestPermissions(permissions, 21)
		else startActivityForResult(pickImageIntent(), PICK_IMAGE_REQUEST)
	}
	
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
			if (data!!.hasExtra("data")) {
				val img = data.extras!!.get("data") as Bitmap
				val uri = ImageFilePath.getImageUri(context, img)
				val extension = ImageFilePath.getExtension(ImageFilePath.getPath(context, uri))
				vm.uploadCoachImageToS3(ImageFilePath.getPath(context, uri), extension)
					.observe(viewLifecycleOwner, Observer { })
			} else {
				val extension = ImageFilePath.getExtension(ImageFilePath.getPath(context, data.data))
				vm.uploadCoachImageToS3(ImageFilePath.getPath(context, data.data), extension)
					.observe(viewLifecycleOwner, Observer { })
			}
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
			else -> return
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
				vm.updateCoach().observe(viewLifecycleOwner, Observer { it ->
					if (it.isSuccess()) {
						binding.nameTV.text = vm.profile.value!!.firstName + " " + vm.profile.value!!.lastName
					}
				})
				return true
			}
		}
		return super.onOptionsItemSelected(item)
	}
}

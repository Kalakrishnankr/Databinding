package com.beachpartnerllc.beachpartner.finder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.ProfileItemBinding
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.user.Profile


/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 24 Sep 2018 at 10:17 AM
 */
class ProfileListingAdapter(private val profiles: List<Profile>, context: Context?) : ArrayAdapter<Profile>
(context, 0){
	
	override fun getItem(position: Int) = profiles[position]
	
	override fun getItemId(position: Int) = position.toLong()
	
	override fun getCount() = profiles.size
	
	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		val holder = if (convertView == null) {
			val binding: ProfileItemBinding = parent!!.bind(R.layout.item_profile)
			binding.root.tag = ViewHolder(binding)
			binding.root.tag as ViewHolder
		} else {
			convertView.tag as ViewHolder
		}
		return holder.bind(getItem(position))
	}
	
	class ViewHolder(private val binding: ProfileItemBinding) {
		fun bind(item: Profile): View {
			binding.profile = item
			binding.executePendingBindings()
			return binding.root
		}
	}
}
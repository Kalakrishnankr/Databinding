package com.beachpartnerllc.beachpartner.finder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.StripFragmentBinding
import com.beachpartnerllc.beachpartner.databinding.StripItemBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseAdapter
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.base.BaseViewHolder
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.user.Profile
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Oct 2018 at 1:09 PM
 */
class StripFragment : BaseFragment() {
	@Inject lateinit var factory: ViewModelProvider.Factory
	private lateinit var binding: StripFragmentBinding
	lateinit var vm: FinderViewModel
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = DataBindingUtil.inflate(inflater,R.layout.fragment_bpstrip_profiles,container,false)
		return binding.root
	}
	
	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		vm = getViewModel(factory)
		binding.vm = vm
		binding.setLifecycleOwner(viewLifecycleOwner)
		vm.getbpProfiles().observe(viewLifecycleOwner, Observer { 
			if (it.isSuccess()){
				binding.stripAdapter = BaseAdapter( it.data!!,
                R.layout.item_avatar,::StripViewHolder)
			}
		})
	}
	
	class StripViewHolder(itemBinding : StripItemBinding):
		BaseViewHolder<Profile,StripItemBinding>(itemBinding){
		
		init {
			itemBinding.itemIV.setOnClickListener{
				Timber.e("Clicked" + itemBinding.item!!)
				
			}
		}
		override fun bindTo(item: Profile) {
			itemBinding.item = item
			Glide.with(itemView).load(item.imageurl).apply(RequestOptions.circleCropTransform().placeholder(R.drawable
				.default_icon))
				.into(itemBinding.itemIV)
			
		}
		
	}
}
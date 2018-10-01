package com.beachpartnerllc.beachpartner.messaging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.MessageBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 03 Oct 2018 at 9:27 AM
 */
class MessageFragment : BaseFragment() {
	@Inject lateinit var factory: ViewModelProvider.Factory
	private lateinit var binding: MessageBinding
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = inflater.bind(R.layout.fragment_message, container)
		return binding.root
	}
	
	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		
		val chatId = MessageFragmentArgs.fromBundle(arguments).chatId
		val vm: MessagingViewModel = getViewModel(factory)
		val options = FirestoreRecyclerOptions.Builder<Message>()
			.setQuery(vm.getMessages(chatId), Message::class.java)
			.setLifecycleOwner(viewLifecycleOwner)
			.build()
		binding.adapter = MessageAdapter(options)
	}
}

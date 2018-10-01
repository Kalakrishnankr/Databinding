package com.beachpartnerllc.beachpartner.messaging

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.MessageItemBinding
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 03 Oct 2018 at 9:28 AM
 */
class MessageAdapter(options: FirestoreRecyclerOptions<Message>) :
	FirestoreRecyclerAdapter<Message, MessageAdapter.ViewHolder>(options) {
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = parent.bind(::ViewHolder, R.layout.item_message)
	
	override fun onBindViewHolder(holder: ViewHolder, p1: Int, item: Message) = holder.bindTo(item)
	
	class ViewHolder(private val binding: MessageItemBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bindTo(item: Message) {
			binding.item = item
			binding.executePendingBindings()
		}
	}
}
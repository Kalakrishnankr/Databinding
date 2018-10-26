package com.beachpartnerllc.beachpartner.messaging

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.MessageFromBinding
import com.beachpartnerllc.beachpartner.databinding.MessageToBinding
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 03 Oct 2018 at 9:28 AM
 */
class MessageAdapter(private val userId: Int, options: FirestoreRecyclerOptions<Message>) :
	FirestoreRecyclerAdapter<Message, RecyclerView.ViewHolder>(options) {
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return when (viewType) {
			R.layout.item_message_from -> parent.bind(::FromVH, viewType)
			else -> parent.bind(::ToVH, viewType)
		}
	}
	
	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int, item: Message) {
		when (holder) {
			is FromVH -> holder.bindTo(item)
			is ToVH -> holder.bindTo(item)
		}
	}
	
	override fun getItemViewType(position: Int): Int {
		return when (userId) {
			getItem(position).senderId -> R.layout.item_message_to
			else -> R.layout.item_message_from
		}
	}
	
	class ToVH(private val binding: MessageToBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bindTo(item: Message) {
			binding.item = item
			binding.executePendingBindings()
		}
	}
	
	class FromVH(private val binding: MessageFromBinding)
		: RecyclerView.ViewHolder(binding.root) {
		fun bindTo(item: Message) {
			binding.item = item
			binding.executePendingBindings()
		}
	}
}
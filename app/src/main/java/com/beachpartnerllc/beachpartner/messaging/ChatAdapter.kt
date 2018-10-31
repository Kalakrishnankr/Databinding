package com.beachpartnerllc.beachpartner.messaging

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.ChatItemBinding
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 03 Oct 2018 at 12:14 PM
 */
class ChatAdapter(options: FirestoreRecyclerOptions<Chat>, private val callback: (Chat) -> Unit) :
    FirestoreRecyclerAdapter<Chat, ChatAdapter.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        parent.bind(::ViewHolder, R.layout.item_chat)

    override fun onBindViewHolder(holder: ViewHolder, p1: Int, chat: Chat) = holder.bindTo(chat)

    inner class ViewHolder(private val binding: ChatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback.invoke(getItem(adapterPosition))
            }
        }

        fun bindTo(item: Chat) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}
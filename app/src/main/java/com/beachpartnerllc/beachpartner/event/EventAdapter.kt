package com.beachpartnerllc.beachpartner.event

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.MasterEventBinding
import com.beachpartnerllc.beachpartner.etc.common.bind

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 18 Sep 2018 at 12:18 PM
 */
class EventAdapter : PagedListAdapter<Event, EventAdapter.ViewHolder>(EVENT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = parent.bind(::ViewHolder, R.layout.item_master_event)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindTo(getItem(position))

    class ViewHolder(private val binding: MasterEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(item: Event?) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    companion object {
        val EVENT_COMPARATOR = object : DiffUtil.ItemCallback<Event>() {
            override fun areContentsTheSame(oldItem: Event, newItem: Event) = oldItem == newItem

            override fun areItemsTheSame(oldItem: Event, newItem: Event) = oldItem.id == newItem.id
        }
    }
}
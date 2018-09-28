package com.beachpartnerllc.beachpartner.etc.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.beachpartnerllc.beachpartner.etc.common.bind

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 14 Jun 2018 at 11:11 AM
 */
open class BaseAdapter<T, B : ViewDataBinding, VH : BaseViewHolder<T, B>>(
	val items: List<T>,
	@LayoutRes private val layoutRes: Int,
	private val construct: (B) -> VH
) : RecyclerView.Adapter<BaseViewHolder<T, B>>() {
	
	override fun getItemCount() = items.size
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = parent.bind(construct, layoutRes)
	
	override fun onBindViewHolder(holder: BaseViewHolder<T, B>, position: Int) {
		holder.bindTo(items[position])
		holder.itemBinding.executePendingBindings()
	}
	
	open fun addItem(item: T): Int {
		(items as ArrayList).add(item)
		notifyItemInserted(items.size - 1)
		return items.size - 1
	}
	
	open fun removeItem(position: Int): T {
		val item = (items as ArrayList).removeAt(position)
		notifyItemRemoved(position)
		return item
	}
}

abstract class BaseViewHolder<T, B : ViewDataBinding>(val itemBinding: B) : RecyclerView.ViewHolder(itemBinding.root) {
	abstract fun bindTo(item: T)
}
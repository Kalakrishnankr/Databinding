package com.beachpartnerllc.beachpartner.etc.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.beachpartnerllc.beachpartner.etc.util.bind

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 14 Jun 2018 at 11:11 AM
 */
class BaseAdapter<T, B : ViewDataBinding>(
        private val items: List<T>,
        @LayoutRes private val layoutRes: Int,
        private val binder: (B, T) -> Unit
) : RecyclerView.Adapter<BaseAdapter<T, B>.BaseViewHolder>() {

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = parent.bind(::BaseViewHolder, layoutRes)

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        binder.invoke(holder.binding, items[position])
        holder.binding.executePendingBindings()
    }

    inner class BaseViewHolder(val binding: B) : RecyclerView.ViewHolder(binding.root)
}
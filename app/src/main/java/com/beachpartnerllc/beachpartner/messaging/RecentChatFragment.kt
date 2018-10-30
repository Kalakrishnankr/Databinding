package com.beachpartnerllc.beachpartner.messaging


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.RecentChatBinding
import com.beachpartnerllc.beachpartner.databinding.RecentChatItemBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseAdapter
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.base.BaseViewHolder
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.home.AthleteHomeFragmentDirections
import javax.inject.Inject


class RecentChatFragment : BaseFragment() {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: RecentChatBinding
    private lateinit var adapter: BaseAdapter<Chat, RecentChatItemBinding, ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflater.bind(R.layout.fragment_recent_chat, container)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val vm: MessagingViewModel = getViewModel(factory)
        vm.getChats().observe(viewLifecycleOwner, Observer {
            if (it.isSuccess()) {
                adapter = BaseAdapter(it.data!!, R.layout.item_recent_chat, ::ViewHolder)
                binding.adapter = adapter
            }
        })
    }

    inner class ViewHolder(itemBinding: RecentChatItemBinding) :
        BaseViewHolder<Chat, RecentChatItemBinding>(itemBinding) {
        init {
            itemBinding.root.setOnClickListener {
                onChatItem(adapter.items[adapterPosition])
            }
        }

        override fun bindTo(item: Chat) {
            itemBinding.item = item
        }
    }

    private fun onChatItem(chat: Chat) {
        val direction = AthleteHomeFragmentDirections.actionMessage().setChatId(chat.id)
        parentFragment?.findNavController()?.navigate(direction)
    }
}

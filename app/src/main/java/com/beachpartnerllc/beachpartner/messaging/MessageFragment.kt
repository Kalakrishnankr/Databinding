package com.beachpartnerllc.beachpartner.messaging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.beachpartnerllc.beachpartner.R
import com.beachpartnerllc.beachpartner.databinding.MessageBinding
import com.beachpartnerllc.beachpartner.etc.base.BaseFragment
import com.beachpartnerllc.beachpartner.etc.common.bind
import com.beachpartnerllc.beachpartner.etc.common.getViewModel
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import javax.inject.Inject


/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 03 Oct 2018 at 9:27 AM
 */
class MessageFragment : BaseFragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: MessageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflater.bind(R.layout.fragment_message, container)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val vm: MessagingViewModel = getViewModel(factory)
        val args = MessageFragmentArgs.fromBundle(arguments)
        vm.initChatForUser(args).observe(viewLifecycleOwner, Observer {
            if (it.isSuccess()) loadChat(it.data!!)
        })
        binding.vm = vm
        binding.setLifecycleOwner(viewLifecycleOwner)
    }

    private fun loadChat(chat: Chat) {
        (activity as AppCompatActivity).supportActionBar!!.title = chat.title

        val vm: MessagingViewModel = getViewModel(factory)
        val options = FirestoreRecyclerOptions.Builder<Message>()
            .setQuery(vm.getCurrentChatMessages(), Message::class.java)
            .setLifecycleOwner(viewLifecycleOwner)
            .build()
        val adapter = MessageAdapter(vm.getSelfId(), options)
        binding.adapter = adapter
        val lm = binding.messagingRV.layoutManager!!
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                lm.smoothScrollToPosition(binding.messagingRV, null, 0)
            }
        })
    }
}

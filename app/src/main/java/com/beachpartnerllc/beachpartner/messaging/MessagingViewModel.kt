package com.beachpartnerllc.beachpartner.messaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import com.google.firebase.Timestamp
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 01 Oct 2018 at 11:48 AM
 */
class MessagingViewModel
@Inject constructor(private val repo: MessagingRepository) : ViewModel() {
    private lateinit var chat: LiveData<Resource<Chat>>

    val message = MutableLiveData<Message>()

    fun initChatForUser(args: MessageFragmentArgs): LiveData<Resource<Chat>> {
        with(args) {
            chat = if (chatId != null) repo.getChat(chatId!!) else repo.getChatForCorrespondent(userId)
        }
        return chat
    }

    fun getChats() = repo.getChats()

    fun getCurrentChatMessages() = repo.getMessages(chat.value!!.data!!.id!!)

    fun getSelfId() = repo.getSelfId()

    fun sendMessage() {
        val msg = message.value!!
        msg.sentAt = Timestamp.now()
        repo.sendMessage(chat.value!!.data!!.id!!, msg)
        message.value = Message().apply { senderId = getSelfId() }
    }

    init {
        message.value = Message().apply { senderId = getSelfId() }
    }
}
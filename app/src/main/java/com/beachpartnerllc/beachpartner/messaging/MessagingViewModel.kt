package com.beachpartnerllc.beachpartner.messaging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 01 Oct 2018 at 11:48 AM
 */
class MessagingViewModel @Inject constructor(private val repo: MessagingRepository) : ViewModel() {
	private lateinit var chatId: String
	val message = MutableLiveData<Message>()
	
	fun init(chatId: String) {
		this.chatId = chatId
	}
	
	fun getChats() = repo.getChats()
	
	fun getCurrentChatMessages() = repo.getMessages(chatId)
	
	fun getSelfId() = repo.getSelfId()
	
	fun sendMessage() {
		val msg = message.value!!
		msg.sentAt = Timestamp.now()
		repo.sendMessage(chatId, msg)
		message.value = Message().apply { senderId = getSelfId() }
	}
	
	init {
		message.value = Message().apply { senderId = getSelfId() }
	}
}
package com.beachpartnerllc.beachpartner.messaging

import androidx.lifecycle.ViewModel
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 01 Oct 2018 at 11:48 AM
 */
class MessagingViewModel @Inject constructor(private val repo: MessagingRepository) : ViewModel() {
	fun getChats() = repo.getChats()
	
	fun getMessages(chatId: String) = repo.getMessages(chatId)
}
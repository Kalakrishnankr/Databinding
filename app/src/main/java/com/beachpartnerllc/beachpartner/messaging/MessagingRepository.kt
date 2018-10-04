package com.beachpartnerllc.beachpartner.messaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beachpartnerllc.beachpartner.etc.common.toObjectWithId
import com.beachpartnerllc.beachpartner.etc.model.pref.Preference
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 03 Oct 2018 at 2:06 PM
 */
class MessagingRepository
@Inject constructor(
	private val db: FirebaseFirestore,
	private val pref: Preference) {
	
	fun getChats(): LiveData<Resource<List<Chat>>> {
		val state = MutableLiveData<Resource<List<Chat>>>()
		state.value = Resource.loading()
		
		db.collection("chat")
			.whereArrayContains(Chat::members.name, pref.userId)
			.addSnapshotListener { snapshot, _ ->
				val chats = arrayListOf<Chat>()
				snapshot?.forEach {
					val chat: Chat = it.toObjectWithId()
					val userId = chat.members?.single { id -> id != pref.userId }
					db.collection("users")
						.whereEqualTo(Member::userId.name, userId).addSnapshotListener { memberSnapShot, _ ->
							val member: Member = memberSnapShot!!.elementAt(0).toObject(Member::class.java)
							chat.avatarUrl = member.avatarUrl
							chat.title = member.name
							state.value = Resource.success(chats)
						}
					chats.add(chat)
				}
			}
		return state
	}
	
	fun getMessages(chatId: String): Query {
		return db.collection("chat")
			.document(chatId)
			.collection("messages")
	}
	
	fun getSelfId() = pref.userId
}
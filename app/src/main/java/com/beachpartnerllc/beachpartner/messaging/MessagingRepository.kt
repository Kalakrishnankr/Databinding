package com.beachpartnerllc.beachpartner.messaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beachpartnerllc.beachpartner.etc.common.toObjectWithId
import com.beachpartnerllc.beachpartner.etc.model.pref.Preference
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Query.Direction.*
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 03 Oct 2018 at 2:06 PM
 */
class MessagingRepository
@Inject constructor(
    private val db: FirebaseFirestore,
    private val pref: Preference
) {

    fun getChats(): LiveData<Resource<List<Chat>>> {
        val state = MutableLiveData<Resource<List<Chat>>>()
        state.value = Resource.loading()

        db.collection("chat")
            .whereArrayContains(Chat::members.name, getSelfId())
            .orderBy(Chat::recent.name + "." + Message::sentAt.name, DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                val chats = arrayListOf<Chat>()
                snapshot?.forEach {
                    val chat: Chat = it.toObjectWithId()
                    val userId = chat.members?.single { id -> id != getSelfId() }!!
                    getMember(userId) {
                        chat.avatarUrl = avatarUrl
                        chat.title = name
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
            .orderBy(Message::sentAt.name, DESCENDING)
    }

    fun getSelfId() = pref.userId

    fun sendMessage(chatId: String, msg: Message) {
        val chat = db.collection("chat").document(chatId)

        chat.collection("messages").add(msg)
        val map = hashMapOf(
            Message::senderId.name to msg.senderId,
            Message::sentAt.name to msg.sentAt,
            Message::content.name to msg.content
        )
        chat.update(Chat::recent.name, map)
    }

    fun getChatForCorrespondent(userId: Int): LiveData<Resource<Chat>> {
        val state = MutableLiveData<Resource<Chat>>()
        state.value = Resource.loading()

        val chatId = getChatIdForUser(userId)
        db.collection("chat").document(chatId)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot?.exists() == true) {
                    val chat: Chat = snapshot.toObjectWithId()
                    getMember(userId) {
                        chat.avatarUrl = avatarUrl
                        chat.title = name
                        state.value = Resource.success(chat)
                    }
                } else {
                    getMember(userId) {
                        val chat = Chat(chatId, listOf(userId, getSelfId()), null)
                        chat.avatarUrl = avatarUrl
                        chat.title = name
                        db.collection("chat").document(chatId).set(chat).addOnSuccessListener {
                            state.value = Resource.success(chat)
                        }
                    }
                }
            }
        return state
    }

    private fun getMember(userId: Int, callback: Member.() -> Unit) {
        db.collection("users").document(userId.toString())
            .addSnapshotListener { snapshot, _ ->
                val member: Member = snapshot!!.toObjectWithId()
                callback(member)
            }
    }

    private fun getChatIdForUser(userId: Int) =
        sequenceOf(userId, getSelfId()).sorted().joinToString("_")

    fun getChat(chatId: String): LiveData<Resource<Chat>> {
        val state = MutableLiveData<Resource<Chat>>()
        state.value = Resource.loading()

        db.collection("chat").document(chatId)
            .addSnapshotListener { snapshot, _ ->
                val chat: Chat = snapshot!!.toObjectWithId()
                val userId = chat.members?.single { id -> id != getSelfId() }
                getMember(userId!!) {
                    chat.avatarUrl = avatarUrl
                    chat.title = name
                    state.value = Resource.success(chat)
                }

            }
        return state
    }
}
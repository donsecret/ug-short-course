package io.codelabs.ugcloudchat.viewmodel.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import io.codelabs.ugcloudchat.model.Chat
import io.codelabs.ugcloudchat.model.database.UGCloudChatDao
import io.codelabs.ugcloudchat.model.preferences.UserSharedPreferences
import io.codelabs.ugcloudchat.util.UGCloudChatConstants
import io.codelabs.ugcloudchat.util.debugThis
import io.codelabs.ugcloudchat.util.getUniqueChatPathWith
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatRepository private constructor(
    val dao: UGCloudChatDao,
    val prefs: UserSharedPreferences,
    val db: FirebaseFirestore
) {
    private var chats: LiveData<PagedList<Chat>>? = null

    // todo: get chats with contacts from firestore directly
    fun getMyChatsWith(uid: String): LiveData<MutableList<Chat>> = TODO("To be implemented soon")

    suspend fun sendMessage(chat: Chat) = withContext(Dispatchers.IO) {
        try {
            Tasks.await(
                db.collection(
                    String.format(
                        UGCloudChatConstants.CHATS_COLLECTION,
                        prefs.uid?.getUniqueChatPathWith(chat.recipient)
                    )
                ).document().apply {
                    chat.key = id
                }.set(chat)
            )
            null
        } catch (e: Exception) {
            debugThis("Unable to send message: ${e.localizedMessage}")
        }
    }

    suspend fun deleteChat(chat: String) = withContext(Dispatchers.IO) {
        /*todo: delete chat*/
    }

    companion object {
        @Volatile
        private var instance: ChatRepository? = null

        fun getInstance(
            dao: UGCloudChatDao,
            prefs: UserSharedPreferences,
            db: FirebaseFirestore
        ): ChatRepository =
            instance ?: synchronized(this) {
                instance ?: ChatRepository(dao, prefs, db).also { instance = it }
            }
    }
}
package dev.csshortcourse.assignmenttwo.datasource

import androidx.lifecycle.LiveData
import dev.csshortcourse.assignmenttwo.model.Chat
import dev.csshortcourse.assignmenttwo.model.User

/**
 * Shows which calls can be made to the respective data sources
 */
interface DataSource {
    suspend fun getUser(id: String): User?
    suspend fun getAllUsers(): MutableList<User>
    suspend fun getMyChats(recipient: String): LiveData<MutableList<Chat>>
    suspend fun addMessage(chat: Chat)
}
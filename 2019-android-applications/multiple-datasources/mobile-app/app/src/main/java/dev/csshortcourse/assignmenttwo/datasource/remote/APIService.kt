package dev.csshortcourse.assignmenttwo.datasource.remote

import dev.csshortcourse.assignmenttwo.model.Chat
import dev.csshortcourse.assignmenttwo.model.User

interface APIService {

    fun getUser(id: String): User?

    fun getAllUsers(): MutableList<User>

    fun getMyChats(chatRequest: ChatRequest): MutableList<Chat>
}

/**
 * Send request body of type:
 *  req.body.sender & req.body.recipient
 */
data class ChatRequest(val sender: String?, val recipient: String)
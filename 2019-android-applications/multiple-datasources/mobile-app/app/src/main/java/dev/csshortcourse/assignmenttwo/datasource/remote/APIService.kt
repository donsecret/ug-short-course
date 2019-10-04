package dev.csshortcourse.assignmenttwo.datasource.remote

import dev.csshortcourse.assignmenttwo.model.Chat
import dev.csshortcourse.assignmenttwo.model.User
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @POST("/users/me")
    suspend fun getUser(id: String): User?

    @GET("/users")
    suspend fun getAllUsers(): MutableList<User>

    @POST("/chats")
    suspend fun getMyChats(chatRequest: ChatRequest): MutableList<Chat>

    @POST("/chats/new")
    suspend fun addMessage(chat: Chat): Void

    @POST("/users/new/multi")
    suspend fun addUsers(users: MutableList<User>): Void
}

/**
 * Send request body of type:
 *  req.body.sender & req.body.recipient
 */
data class ChatRequest(val sender: String?, val recipient: String)
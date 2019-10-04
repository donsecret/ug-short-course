package dev.csshortcourse.assignmenttwo.datasource.remote

import dev.csshortcourse.assignmenttwo.model.Chat
import dev.csshortcourse.assignmenttwo.model.User
import retrofit2.http.*

interface APIService {
    @POST("/users/{id}")
    suspend fun getUser(@Path("id") id: String): User?

    @GET("/users")
    suspend fun getAllUsers(): MutableList<User>

    @POST("/chats")
    suspend fun getMyChats(@Body chatRequest: ChatRequest): MutableList<Chat>

    @POST("/chats/new")
    suspend fun addMessage(@Body chat: Chat): Void

    @POST("/users/new/multi")
    suspend fun addUsers(@Body users: MutableList<User>): Void

    @DELETE("/chats/{id}")
    suspend fun deleteMessage(@Path("id") id: String): Void
}

/**
 * Send request body of type:
 *  req.body.sender & req.body.recipient
 */
data class ChatRequest(val sender: String?, val recipient: String)
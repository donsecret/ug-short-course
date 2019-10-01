package dev.csshortcourse.assignmenttwo.datasource.remote

import dev.csshortcourse.assignmenttwo.model.Chat
import dev.csshortcourse.assignmenttwo.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @POST("/users/me")
    fun getUser(id: String): /*Call<User?>*/ User?

    @GET("/users")
    fun getAllUsers(): /*Call<MutableList<User>>*/ MutableList<User>

    @POST("/chats")
    fun getMyChats(chatRequest: ChatRequest): /*Call<MutableList<Chat>>*/ MutableList<Chat>
}

/**
 * Send request body of type:
 *  req.body.sender & req.body.recipient
 */
data class ChatRequest(val sender: String?, val recipient: String)
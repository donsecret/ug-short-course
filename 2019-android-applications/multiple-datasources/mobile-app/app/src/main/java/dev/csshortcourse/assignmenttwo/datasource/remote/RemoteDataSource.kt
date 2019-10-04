package dev.csshortcourse.assignmenttwo.datasource.remote

import android.app.Application
import dev.csshortcourse.assignmenttwo.datasource.DataSource
import dev.csshortcourse.assignmenttwo.model.Chat
import dev.csshortcourse.assignmenttwo.model.User
import dev.csshortcourse.assignmenttwo.preferences.AppPreferences
import dev.csshortcourse.assignmenttwo.util.debugger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Remote data source
 */
class RemoteDataSource constructor(app: Application) : DataSource {
    private val prefs: AppPreferences by lazy { AppPreferences.get(app) }
    private val apiService: APIService by lazy { APIBuilder.getService() }

    override suspend fun getUser(id: String): User? {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getUser(id)
            } catch (e: Exception) {
                debugger(e.localizedMessage)
                null
            }
        }
    }

    override suspend fun getAllUsers(): MutableList<User> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getAllUsers()
            } catch (e: Exception) {
                debugger(e.localizedMessage)
                mutableListOf<User>()
            }
        }
    }

    override suspend fun getMyChats(recipient: String): MutableList<Chat> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getMyChats(ChatRequest(prefs.userId, recipient))
            } catch (e: Exception) {
                debugger(e.localizedMessage)
                mutableListOf<Chat>()
            }
        }
    }

    override suspend fun addMessage(chat: Chat) {
        withContext(Dispatchers.IO) {
            try {
                apiService.addMessage(chat)
            } catch (e: Exception) {
                debugger(e.localizedMessage)
            }
        }
    }

    suspend fun addUsers(users: MutableList<User>) {
        withContext(Dispatchers.IO) {
            try {
                apiService.addUsers(users)
            } catch (e: Exception) {
                debugger(e.localizedMessage)
            }
        }
    }
}
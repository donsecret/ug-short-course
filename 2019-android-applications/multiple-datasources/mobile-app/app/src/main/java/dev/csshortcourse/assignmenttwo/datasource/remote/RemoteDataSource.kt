package dev.csshortcourse.assignmenttwo.datasource.remote

import android.app.Application
import dev.csshortcourse.assignmenttwo.datasource.DataSource
import dev.csshortcourse.assignmenttwo.model.Chat
import dev.csshortcourse.assignmenttwo.model.User
import dev.csshortcourse.assignmenttwo.preferences.AppPreferences
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
            apiService.getUser(id)
        }
    }

    override suspend fun getAllUsers(): MutableList<User> {
        return withContext(Dispatchers.IO) {
            apiService.getAllUsers()
        }
    }

    override suspend fun getMyChats(recipient: String): MutableList<Chat> {
        return withContext(Dispatchers.IO) {
            apiService.getMyChats(ChatRequest(prefs.userId, recipient))
        }
    }
}
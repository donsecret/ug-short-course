package dev.csshortcourse.assignmenttwo.datasource.local

import android.app.Application
import dev.csshortcourse.assignmenttwo.datasource.DataSource
import dev.csshortcourse.assignmenttwo.model.Chat
import dev.csshortcourse.assignmenttwo.model.User
import dev.csshortcourse.assignmenttwo.preferences.AppPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Local data source
 */
class LocalDataSource constructor(app: Application) : DataSource {
    private val prefs: AppPreferences by lazy { AppPreferences.get(app) }
    private val database: LocalDatabase by lazy { LocalDatabase.get(app) }
    val chatDao: ChatDao = database.chatDao()
    val userDao: UserDao = database.userDao()

    override suspend fun getUser(id: String): User? {
        return withContext(Dispatchers.IO) {
            userDao.getUser(id)
        }
    }

    override suspend fun getAllUsers(): MutableList<User> {
        return withContext(Dispatchers.IO) {
            userDao.getAllUsers()
        }
    }

    override suspend fun getMyChats(recipient: String): MutableList<Chat> {
        return withContext(Dispatchers.IO) {
            chatDao.getMyChats(prefs.userId, recipient)
        }
    }

    override suspend fun addMessage(chat: Chat) = chatDao.insert(chat)
}
package dev.csshortcourse.assignmenttwo.datasource.local

import android.app.Application
import dev.csshortcourse.assignmenttwo.datasource.DataSource
import dev.csshortcourse.assignmenttwo.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Local data source
 */
class LocalDataSource constructor(app: Application) : DataSource {
    private val database: LocalDatabase by lazy { LocalDatabase.get(app) }
    private val chatDao: ChatDao = database.chatDao()
    private val userDao: UserDao = database.userDao()

    override suspend fun getUser(id: String): User? {
        return withContext(Dispatchers.IO) {
            userDao.getUser(id)
        }
    }
}
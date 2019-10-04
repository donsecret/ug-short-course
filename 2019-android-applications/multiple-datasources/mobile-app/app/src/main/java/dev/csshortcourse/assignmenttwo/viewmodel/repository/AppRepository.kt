package dev.csshortcourse.assignmenttwo.viewmodel.repository

import android.app.Application
import dev.csshortcourse.assignmenttwo.datasource.DataSource
import dev.csshortcourse.assignmenttwo.datasource.local.LocalDataSource
import dev.csshortcourse.assignmenttwo.datasource.remote.RemoteDataSource
import dev.csshortcourse.assignmenttwo.model.Chat
import dev.csshortcourse.assignmenttwo.model.User
import dev.csshortcourse.assignmenttwo.preferences.AppPreferences
import dev.csshortcourse.assignmenttwo.util.WorkState
import dev.csshortcourse.assignmenttwo.viewmodel.AppViewModel

// Callback alias
typealias Callback<O> = (WorkState, O?) -> Unit

/**
 * Base repository class
 * Defines which calls can be made to the data sources
 * Same as [DataSource] interface calls
 */
interface Repository {
    suspend fun getCurrentUser(refresh: Boolean): User?
    suspend fun getMyChats(refresh: Boolean, recipient: String): MutableList<Chat>
    suspend fun getUsers(refresh: Boolean): MutableList<User>
    suspend fun getUser(refresh: Boolean, id: String): User?
    suspend fun addMessage(chat: Chat)
    suspend fun addUsers(users: MutableList<User>)
    suspend fun deleteMessage(chat: Chat)
    fun login(callback: Callback<User>)
    fun logout(callback: Callback<Void>)
}

/**
 * [Repository] for the [AppViewModel]
 */
class AppRepository private constructor(app: Application) : Repository {
    private val prefs: AppPreferences by lazy { AppPreferences.get(app) }
    // Local data source
    private val localDataSource: LocalDataSource by lazy { LocalDataSource(app) }
    // Remote data source
    private val remoteDataSource: RemoteDataSource by lazy { RemoteDataSource(app) }

    override suspend fun getUsers(refresh: Boolean): MutableList<User> {
        return if (refresh) remoteDataSource.getAllUsers() else localDataSource.getAllUsers()
    }

    override suspend fun getCurrentUser(refresh: Boolean): User? {
        return when {
            prefs.userId.isNullOrEmpty() -> null
            refresh -> remoteDataSource.getUser(
                prefs.userId!!
            ).apply { if (this != null) localDataSource.userDao.insert(this) }
            else -> localDataSource.getUser(prefs.userId!!)
        }
    }

    override suspend fun getMyChats(refresh: Boolean, recipient: String): MutableList<Chat> {
        return if (refresh) remoteDataSource.getMyChats(recipient) else localDataSource.getMyChats(
            recipient
        )
    }

    override fun login(callback: Callback<User>) {
        TODO()
    }

    override fun logout(callback: Callback<Void>) {
        TODO()
    }

    override suspend fun addMessage(chat: Chat) {
        localDataSource.addMessage(chat).apply { remoteDataSource.addMessage(chat) }
    }

    override suspend fun getUser(refresh: Boolean, id: String): User? {
        return if (refresh)
            remoteDataSource.getUser(id).apply {
                if (this != null) localDataSource.userDao.insert(
                    this
                )
            } else localDataSource.getUser(id)
    }

    override suspend fun addUsers(users: MutableList<User>) {
        localDataSource.userDao.insertAll(users)
        remoteDataSource.addUsers(users)
    }

    override suspend fun deleteMessage(chat: Chat) {
        localDataSource.chatDao.remove(chat)
        remoteDataSource.deleteMessage(chat.id)
    }

    companion object {
        /**
         * Creating singleton
         */
        @Volatile
        private var instance: AppRepository? = null

        fun get(app: Application): AppRepository = instance ?: synchronized(this) {
            instance ?: AppRepository(app).also { instance = it }
        }
    }

}
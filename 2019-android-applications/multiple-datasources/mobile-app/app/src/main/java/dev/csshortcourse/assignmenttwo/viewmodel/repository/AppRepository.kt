package dev.csshortcourse.assignmenttwo.viewmodel.repository

import android.app.Application
import androidx.lifecycle.LiveData
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
    suspend fun getMyChats(refresh: Boolean, recipient: String): LiveData<MutableList<Chat>>
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

    // todo: fix this
    override suspend fun getUsers(refresh: Boolean): MutableList<User> {
        return if (refresh) localDataSource.getAllUsers().filter { !prefs.userId.isNullOrEmpty() && it.id != prefs.userId }.toMutableList()
        else localDataSource.getAllUsers().filter { !prefs.userId.isNullOrEmpty() && it.id != prefs.userId }.toMutableList()
    }

    // todo: fix this
    override suspend fun getCurrentUser(refresh: Boolean): User? {
        return when {
            prefs.userId.isNullOrEmpty() -> null
            refresh -> localDataSource.getUser(prefs.userId!!)
            else -> localDataSource.getUser(prefs.userId!!)
        }
    }

    // todo: fix this
    override suspend fun getMyChats(
        refresh: Boolean,
        recipient: String
    ): LiveData<MutableList<Chat>> {
        return if (refresh) localDataSource.getMyChats(recipient)
        else localDataSource.getMyChats(recipient)
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

    // todo: fix this
    override suspend fun getUser(refresh: Boolean, id: String): User? {
        return if (refresh) localDataSource.getUser(id)
        else localDataSource.getUser(id)
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
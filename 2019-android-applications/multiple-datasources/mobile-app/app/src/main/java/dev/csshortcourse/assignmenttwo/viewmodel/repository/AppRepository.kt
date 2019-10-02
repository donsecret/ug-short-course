package dev.csshortcourse.assignmenttwo.viewmodel.repository

import android.app.Application
import dev.csshortcourse.assignmenttwo.datasource.DataSource
import dev.csshortcourse.assignmenttwo.datasource.local.LocalDataSource
import dev.csshortcourse.assignmenttwo.datasource.remote.RemoteDataSource
import dev.csshortcourse.assignmenttwo.model.Chat
import dev.csshortcourse.assignmenttwo.model.User
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
    fun getCurrentUser(refresh: Boolean): User
    fun getMyChats(refresh: Boolean): MutableList<Chat>
    suspend fun getUsers(refresh: Boolean): MutableList<User>
    fun login(callback: Callback<User>)
    fun logout(callback: Callback<Void>)
}

/**
 * [Repository] for the [AppViewModel]
 */
class AppRepository private constructor(app: Application) : Repository {
    // Local data source
    private val localDataSource: LocalDataSource by lazy { LocalDataSource(app) }
    // Remote data source
    private val remoteDataSource: RemoteDataSource by lazy { RemoteDataSource(app) }

    override suspend fun getUsers(refresh: Boolean): MutableList<User> {
        return if (refresh) remoteDataSource.getAllUsers() else localDataSource.getAllUsers()
    }

    override fun getCurrentUser(refresh: Boolean): User {
        TODO()
    }

    override fun getMyChats(refresh: Boolean): MutableList<Chat> {
        TODO()
    }

    override fun login(callback: Callback<User>) {
        TODO()
    }

    override fun logout(callback: Callback<Void>) {
        TODO()
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
package io.codelabs.ugcloudchat.viewmodel.repository

import androidx.lifecycle.LiveData
import io.codelabs.ugcloudchat.model.WhatsappUser
import io.codelabs.ugcloudchat.model.database.UGCloudChatDao
import io.codelabs.ugcloudchat.model.preferences.UserSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for making calls to the user database
 */
class UserRepository private constructor(
    private val dao: UGCloudChatDao,
    private val prefs: UserSharedPreferences
) {

    fun getAllUsers(): LiveData<MutableList<WhatsappUser>> = dao.getAllUsersAsync()

    suspend fun removeUser(user: WhatsappUser) = withContext(Dispatchers.IO) {
        dao.removeUser(user)
    }

    suspend fun setCurrentUser(user: WhatsappUser) = withContext(Dispatchers.IO) {
        dao.setCurrentUser(user)
    }

    fun getCurrentUser(): LiveData<WhatsappUser> = dao.getCurrentUser(prefs.uid)

    suspend fun addSingleUser(user: WhatsappUser) = withContext(Dispatchers.IO) {
        dao.addSingleUser(user)
    }

    suspend fun updateUser(vararg users: WhatsappUser) = withContext(Dispatchers.IO) {
        dao.updateUsers(*users)
    }

    fun getUserByUID(uid: String): LiveData<WhatsappUser> = dao.getUserByUID(uid)

    fun getUserById(id: Long): LiveData<WhatsappUser> = dao.getUserById(id)

    suspend fun addUsers(users: MutableList<WhatsappUser>) = withContext(Dispatchers.IO) {
        dao.addUsers(users)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(dao: UGCloudChatDao, prefs: UserSharedPreferences): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(dao, prefs).also { instance = it }
            }
    }
}
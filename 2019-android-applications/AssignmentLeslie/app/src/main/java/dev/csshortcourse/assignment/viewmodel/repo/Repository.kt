package dev.csshortcourse.assignment.viewmodel.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.csshortcourse.assignment.model.User
import dev.csshortcourse.assignment.viewmodel.FirebaseDataSource

/**
 * Base repository to show which calls are allowed to be made to the backend
 */
interface BaseRepository {
    fun createAccount(username: String, email: String, password: String)
    fun login(email: String, password: String)
    fun getCurrentUser(): LiveData<User>
}

class Repository(private val datasource: FirebaseDataSource) : BaseRepository {

    override fun createAccount(username: String, email: String, password: String) {
        // todo: create account with data source
    }

    override fun login(email: String, password: String) {
        // todo: login user with datasource
        datasource.login(email, password) { user ->
            // User logged in successfully
            println("Assignment: $user")
        }
    }

    override fun getCurrentUser(): LiveData<User> {
        val liveUser = MutableLiveData<User>()
        // todo: use data source to fetch user information from the server
        return liveUser
    }

}
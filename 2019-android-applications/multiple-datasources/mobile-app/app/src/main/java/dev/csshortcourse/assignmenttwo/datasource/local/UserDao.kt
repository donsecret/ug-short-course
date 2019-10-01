package dev.csshortcourse.assignmenttwo.datasource.local

import dev.csshortcourse.assignmenttwo.model.User

/**
 * Data Access Object for [User]
 */
interface UserDao : BaseDao<User> {
    // @Query("select * from users where id = :id")
    fun getUser(id: String): User

    // @Query("select * from users order by timestamp desc")
    fun getAllUsers(): MutableList<User>
}
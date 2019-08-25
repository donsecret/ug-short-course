package dev.ugscheduler.domain.repository

import dev.ugscheduler.domain.model.User

/**
 * User repository
 */
interface UserRepository {
    fun getUsers(refresh: Boolean): MutableList<User>
    fun getUser(userId: String, refresh: Boolean): User
}
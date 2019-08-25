package dev.ugscheduler.domain.repository

import dev.ugscheduler.domain.model.User

/**
 * User repository
 */
interface UserRepository {
    fun get(refresh: Boolean) : MutableList<User>
}
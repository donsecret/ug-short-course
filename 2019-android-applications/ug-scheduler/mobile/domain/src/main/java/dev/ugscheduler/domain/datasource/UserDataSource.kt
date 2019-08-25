package dev.ugscheduler.domain.datasource

import dev.ugscheduler.domain.model.User

interface UserDataSource {
    fun getUsers(refresh: Boolean): MutableList<User>
    fun getSingleUser(userId: String, refresh: Boolean): User
}
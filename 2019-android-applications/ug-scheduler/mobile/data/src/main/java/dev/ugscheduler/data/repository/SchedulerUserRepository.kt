package dev.ugscheduler.data.repository

import dev.ugscheduler.domain.datasource.UserDataSource
import dev.ugscheduler.domain.model.User
import dev.ugscheduler.domain.repository.UserRepository

// TODO: INJECT THIS as [UserRepository]
open class SchedulerUserRepository(private val datasource: UserDataSource) : UserRepository {
    override fun getUsers(refresh: Boolean): MutableList<User> = datasource.getUsers(refresh)
    override fun getUser(userId: String, refresh: Boolean): User =
        datasource.getSingleUser(userId, refresh)
}
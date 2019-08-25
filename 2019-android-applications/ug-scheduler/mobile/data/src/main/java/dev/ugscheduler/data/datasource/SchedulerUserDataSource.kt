package dev.ugscheduler.data.datasource

import dev.ugscheduler.domain.datasource.UserDataSource
import dev.ugscheduler.domain.model.User

// TODO: INJECT THIS AS [UserDataSource]
class SchedulerUserDataSource(
    /*TODO: add remote and local data sources here*/
) : UserDataSource {
    override fun getUsers(refresh: Boolean): MutableList<User> {
        when (refresh) {
            true -> {
                // Todo: fetch from remote source and cache locally
            }

            false -> {
                // Todo: Fetch locally
            }
        }
        TODO()
    }

    override fun getSingleUser(userId: String, refresh: Boolean): User {
        when (refresh) {
            true -> {
                // Todo: fetch from remote source and cache locally
            }

            false -> {
                // Todo: Fetch locally
            }
        }
        TODO()
    }
}
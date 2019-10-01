package dev.csshortcourse.assignmenttwo.datasource

import dev.csshortcourse.assignmenttwo.model.User

/**
 * Shows which calls can be made to the respective data sources
 */
interface DataSource {
    suspend fun getUser(id: String): User?
}
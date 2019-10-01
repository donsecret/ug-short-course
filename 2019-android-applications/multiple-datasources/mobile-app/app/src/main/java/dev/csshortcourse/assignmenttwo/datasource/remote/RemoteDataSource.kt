package dev.csshortcourse.assignmenttwo.datasource.remote

import android.app.Application
import dev.csshortcourse.assignmenttwo.datasource.DataSource
import dev.csshortcourse.assignmenttwo.model.User

/**
 * Remote data source
 */
class RemoteDataSource constructor(app: Application) : DataSource {

    override suspend fun getUser(id: String): User? {
        TODO()
    }
}
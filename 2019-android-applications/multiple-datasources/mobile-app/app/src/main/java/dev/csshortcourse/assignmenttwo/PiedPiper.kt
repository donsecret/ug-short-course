package dev.csshortcourse.assignmenttwo

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dev.csshortcourse.assignmenttwo.datasource.local.LocalDatabase
import dev.csshortcourse.assignmenttwo.datasource.local.UserDao
import dev.csshortcourse.assignmenttwo.model.User
import dev.csshortcourse.assignmenttwo.preferences.AppPreferences
import dev.csshortcourse.assignmenttwo.worker.DatabaseWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PiedPiper : Application() {
    private val prefs: AppPreferences by lazy { AppPreferences.get(applicationContext) }
    private val userDao: UserDao by lazy { LocalDatabase.get(applicationContext).userDao() }

    override fun onCreate() {
        super.onCreate()

        // set dummy user
        if (prefs.userId.isNullOrEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                val user = User("Quabynah Bilson")
                userDao.insert(user)
                prefs.login(user.id)
            }
        }

        // Start worker to add data to database
        with(WorkManager.getInstance(applicationContext)) {
            val builder = OneTimeWorkRequestBuilder<DatabaseWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
            enqueue(mutableListOf(builder.build()))
        }

    }
}
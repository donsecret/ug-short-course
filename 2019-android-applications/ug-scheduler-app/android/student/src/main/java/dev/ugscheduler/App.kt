/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler

import android.app.Application
import android.os.StrictMode
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dev.ugscheduler.shared.di.loadAppModules
import dev.ugscheduler.shared.prefs.AppPreferences
import dev.ugscheduler.shared.worker.CourseWorker
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            enableStrictMode()
            Timber.plant(Timber.DebugTree())
        }
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(loadAppModules())
        }

        // Set default theme
        val prefs by inject<AppPreferences>()
        prefs.setDarkMode(prefs.currentMode)

        // Start all workers
        startWorkers()
    }

    private fun enableStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )
    }

    private fun startWorkers() {
        with(WorkManager.getInstance(applicationContext)) {
            enqueue(OneTimeWorkRequestBuilder<CourseWorker>().build())
        }
    }
}
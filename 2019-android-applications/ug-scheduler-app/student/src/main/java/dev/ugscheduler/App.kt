/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import android.app.Application
import dev.ugscheduler.BuildConfig.DEBUG
import dev.ugscheduler.shared.di.loadAppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Application subclass
 */
class App : Application() {

    override fun onCreate() {
        if (DEBUG) enableStrictMode()
        super.onCreate()
        if (DEBUG) Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@App)
            modules(loadAppModules())
        }
    }

    private fun enableStrictMode() {
        StrictMode.setThreadPolicy(
            Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )
    }
}
/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import dev.ugscheduler.BuildConfig.DEBUG
import dev.ugscheduler.shared.di.loadAppModules
import dev.ugscheduler.shared.prefs.AppPreferences
import dev.ugscheduler.shared.util.debugger
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Application subclass
 */
class App : Application(), LifecycleOwner {
    override fun getLifecycle(): Lifecycle {
        return LifecycleRegistry(this)
    }

    override fun onCreate() {
        if (DEBUG) enableStrictMode()
        super.onCreate()
        if (DEBUG) Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@App)
            modules(loadAppModules())
        }

        // Night mode activation
        val prefs: AppPreferences by inject()
        prefs.setDarkMode(AppCompatDelegate.getDefaultNightMode())
        prefs.nightMode.observe(this, Observer {
            debugger("Night mode: $it")
        })
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
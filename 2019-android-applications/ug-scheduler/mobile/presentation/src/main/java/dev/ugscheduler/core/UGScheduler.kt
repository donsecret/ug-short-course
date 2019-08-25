package dev.ugscheduler.core

import android.app.Application
import dev.ugscheduler.BuildConfig.DEBUG
import timber.log.Timber

class UGScheduler : Application() {

    override fun onCreate() {
        super.onCreate()

        // Plant Timber Tree
        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}
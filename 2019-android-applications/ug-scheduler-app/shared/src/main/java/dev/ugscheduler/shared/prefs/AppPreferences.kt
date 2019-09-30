/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.shared.prefs

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.ugscheduler.shared.util.Constants

/**
 * Application instance
 */
class AppPreferences private constructor(context: Application) {
    private val prefs = context.getSharedPreferences(Constants.APP_PREFS, Context.MODE_PRIVATE)
    private val _nightMode = MutableLiveData<Boolean>()


    companion object {
        private const val NIGHT_MODE_KEY = "night_mode_key"
        @Volatile
        private var instance: AppPreferences? = null

        fun get(app: Application): AppPreferences = instance ?: synchronized(this) {
            instance ?: AppPreferences(app).also { instance = it }
        }
    }

    init {
        _nightMode.value = prefs.getBoolean(NIGHT_MODE_KEY, false)
    }

    fun setDarkMode(mode: Int = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
        when (mode) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
            AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY,
            AppCompatDelegate.MODE_NIGHT_YES -> {
                /*Night mode active*/
                prefs.edit {
                    putBoolean(NIGHT_MODE_KEY, true)
                    apply()
                }
                _nightMode.value = true
            }

            AppCompatDelegate.MODE_NIGHT_NO -> {
                /*Night mode inactive*/
                prefs.edit {
                    putBoolean(NIGHT_MODE_KEY, false)
                    apply()
                }
                _nightMode.value = false
            }

            else -> {
                prefs.edit {
                    putBoolean(NIGHT_MODE_KEY, false)
                    apply()
                }
                _nightMode.value = false
            }
        }

        // Set dark theme
        AppCompatDelegate.setDefaultNightMode(
            if (_nightMode.value != null && _nightMode.value == true
            ) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    val nightMode: LiveData<Boolean> = _nightMode

}
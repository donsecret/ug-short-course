package dev.csshortcourse.assignmenttwo.util

import androidx.appcompat.app.AppCompatActivity
import dev.csshortcourse.assignmenttwo.preferences.AppPreferences

/**
 * Base class for all activities
 */
open class BaseActivity : AppCompatActivity() {
    // Get the Shared Preferences instance
    val prefs: AppPreferences by lazy { AppPreferences.get(applicationContext) }

}
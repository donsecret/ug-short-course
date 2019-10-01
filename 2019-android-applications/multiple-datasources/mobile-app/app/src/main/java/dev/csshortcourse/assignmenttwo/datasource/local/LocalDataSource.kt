package dev.csshortcourse.assignmenttwo.datasource.local

import dev.csshortcourse.assignmenttwo.datasource.DataSource
import dev.csshortcourse.assignmenttwo.preferences.AppPreferences

/**
 * Local data source
 */
class LocalDataSource constructor(private val prefs: AppPreferences) : DataSource {

}
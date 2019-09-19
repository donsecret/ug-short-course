package dev.csshortcourse.shared.view

import androidx.appcompat.app.AppCompatActivity
import dev.csshortcourse.shared.util.currentContext
import dev.csshortcourse.shared.util.debugger

/**
 * A base class for all activities
 */
open class BaseActivity : AppCompatActivity() {

    init {
        debugger(currentContext)
    }

}
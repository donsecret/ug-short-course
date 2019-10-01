package dev.csshortcourse.assignmenttwo.view

import android.os.Bundle
import dev.csshortcourse.assignmenttwo.R
import dev.csshortcourse.assignmenttwo.util.BaseActivity

/**
 * This assignment is mainly focused on working with multiple data sources in an application
 * This is a simple chat application that works with multiple data source( local and remote)
 *
 * Remote data source: NodeJS
 * Local data source: Room
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

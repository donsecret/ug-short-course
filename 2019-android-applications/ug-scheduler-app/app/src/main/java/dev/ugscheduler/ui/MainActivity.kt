package dev.ugscheduler.ui

import android.os.Bundle
import android.view.View
import dev.ugscheduler.R
import dev.ugscheduler.shared.util.BaseActivity
import dev.ugscheduler.shared.util.intentTo
import dev.ugscheduler.ui.home.HomeActivity

/**
 * Main entry point of application
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getStarted(view: View) = intentTo(HomeActivity::class.java, true)
}

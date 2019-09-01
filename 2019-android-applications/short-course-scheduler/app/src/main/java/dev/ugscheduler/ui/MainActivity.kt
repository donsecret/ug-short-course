package dev.ugscheduler.ui

import android.os.Bundle
import android.view.View
import dev.ugscheduler.R
import dev.ugscheduler.shared.util.BaseActivity
import dev.ugscheduler.shared.util.intentTo

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getStarted(view: View) {
        // todo: Start the application in guest mode
        intentTo(HomeActivity::class.java, true)
    }
}

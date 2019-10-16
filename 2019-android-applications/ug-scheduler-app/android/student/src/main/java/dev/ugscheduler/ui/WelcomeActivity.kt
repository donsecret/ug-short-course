package dev.ugscheduler.ui

import android.os.Bundle
import dev.ugscheduler.R
import dev.ugscheduler.shared.util.BaseActivity

class WelcomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }
}

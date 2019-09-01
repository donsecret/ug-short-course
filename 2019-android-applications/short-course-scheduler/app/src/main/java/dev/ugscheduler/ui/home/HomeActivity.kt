package dev.ugscheduler.ui.home

import android.os.Bundle
import dev.ugscheduler.R
import dev.ugscheduler.shared.util.BaseActivity

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}
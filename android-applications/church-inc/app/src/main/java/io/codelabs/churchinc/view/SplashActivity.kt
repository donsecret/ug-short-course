package io.codelabs.churchinc.view

import android.os.Bundle
import io.codelabs.churchinc.R
import io.codelabs.churchinc.core.RootActivity

class SplashActivity : RootActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

}
package io.codelabs.churchinc.view

import android.os.Bundle
import io.codelabs.churchinc.R
import io.codelabs.churchinc.core.RootActivity

class HomeActivity : RootActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}

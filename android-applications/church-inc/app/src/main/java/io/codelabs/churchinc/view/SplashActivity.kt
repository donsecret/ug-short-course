package io.codelabs.churchinc.view

import android.os.Bundle
import android.widget.Toast
import com.google.firebase.FirebaseApp
import io.codelabs.churchinc.R
import io.codelabs.churchinc.core.RootActivity

/**
 * This is  the welcome screen of the application
 */
class SplashActivity : RootActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ATTACH A LAYOUT TO THIS ACTIVITY
        setContentView(R.layout.activity_splash)

    }

}
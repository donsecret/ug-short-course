package io.codelabs.churchinc.view

import android.os.Bundle
import io.codelabs.churchinc.R
import io.codelabs.churchinc.core.RootActivity
import io.codelabs.churchinc.util.debugLog
import kotlinx.coroutines.launch

/**
 * This is  the welcome screen of the application
 */
class SplashActivity : RootActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ATTACH A LAYOUT TO THIS ACTIVITY
        setContentView(R.layout.activity_splash)

        ioScope.launch {
            val currentUser = dao.getCurrentUser(auth.currentUser?.uid ?: "jhj")

            uiScope.launch {
                debugLog("User is: $currentUser")
            }
        }
    }

}
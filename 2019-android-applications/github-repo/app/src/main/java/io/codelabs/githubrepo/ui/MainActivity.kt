/*
 * Copyright (c) 2019. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package io.codelabs.githubrepo.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import io.codelabs.githubrepo.R
import io.codelabs.githubrepo.databinding.ActivityMainBinding
import io.codelabs.githubrepo.shared.core.base.BaseActivity
import io.codelabs.githubrepo.shared.core.prefs.AppSharedPreferences
import io.codelabs.githubrepo.shared.util.debugger
import io.codelabs.githubrepo.shared.util.intentTo
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val prefs by inject<AppSharedPreferences>()
    private val auth by inject<FirebaseAuth>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("CAST_NEVER_SUCCEEDS")
        binding = DataBindingUtil.setContentView<ViewDataBinding>(
            this,
            R.layout.activity_main
        ) as ActivityMainBinding

        // Listen for live events of user login
        prefs.liveLoginStatus.observe(this, Observer { state ->
            if (state) {
                // Navigates users to home screen when they are logged
                intentTo(HomeActivity::class.java, true)
            }
        })

        onNewIntent(intent)
    }

    fun login(view: View) = setupGitHubAuthFlow()

    // Creates the Sign-In flow for GitHub
    private fun setupGitHubAuthFlow() {
        val provider = OAuthProvider.newBuilder("github.com")

        // Start login with OAuth
        with(auth.pendingAuthResult) {
            // There is a sign-in here
            this?.addOnSuccessListener(this@MainActivity) { task ->
                // User is signed in.
                // IdP data available in
                // authResult.getAdditionalUserInfo().getProfile().
                // The OAuth access token can also be retrieved:
                // authResult.getCredential().getAccessToken().
                val profile = task?.additionalUserInfo?.profile
                debugger("Profile after login: $profile")
            }?.addOnFailureListener(this@MainActivity) { exception ->
                debugger(exception.localizedMessage)
                // Handle failure
            }
                ?: // Start sign-in flow
                auth.startActivityForSignInWithProvider(this@MainActivity, provider.build())
                    .addOnSuccessListener(this@MainActivity) { task ->
                        // User is signed in.
                        // IdP data available in
                        // authResult.getAdditionalUserInfo().getProfile().
                        // The OAuth access token can also be retrieved:
                        // authResult.getCredential().getAccessToken().
                        val profile = task?.additionalUserInfo?.profile
                        debugger("Profile after login: $profile")
                    }.addOnFailureListener(this@MainActivity) { exception ->
                        debugger(exception.localizedMessage)
                        // Handle failure
                    }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        debugger(intent?.data)
    }
}

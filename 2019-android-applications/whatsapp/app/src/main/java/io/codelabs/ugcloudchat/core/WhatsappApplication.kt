package io.codelabs.ugcloudchat.core

import android.app.Application
import com.google.firebase.FirebaseApp
import io.codelabs.ugcloudchat.util.debugThis

class WhatsappApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase SDK
        FirebaseApp.initializeApp(this).apply {
            debugThis(this?.name)
        }
    }

}
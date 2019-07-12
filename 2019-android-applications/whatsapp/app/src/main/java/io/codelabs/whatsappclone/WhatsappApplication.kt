package io.codelabs.whatsappclone

import android.app.Application
import com.google.firebase.FirebaseApp

class WhatsappApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase SDK
        FirebaseApp.initializeApp(this).apply {
            debugThis(this?.name)
        }
    }

}
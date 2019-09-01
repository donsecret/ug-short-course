package dev.ugscheduler.shared.di

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import dev.ugscheduler.shared.util.Constants
import dev.ugscheduler.shared.util.prefs.UserSharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

// Load all modules declared below from here
fun loadAppModules() = mutableListOf(firebaseModule, appPrefsModule)

private val firebaseModule = module {
    single { FirebaseApp.initializeApp(androidContext()) }
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance().reference.child(Constants.BUCKET_NAME) }
    single { FirebaseMessaging.getInstance() }
}

private val appPrefsModule = module {
    single { UserSharedPreferences.getInstance(androidContext()) }
}


/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.shared.di

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import dev.ugscheduler.shared.datasource.local.LocalDataSource
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
    single { LocalDataSource.get(androidContext()) }
    single { get<LocalDataSource>().studentDao() }
    single { get<LocalDataSource>().courseDao() }
}


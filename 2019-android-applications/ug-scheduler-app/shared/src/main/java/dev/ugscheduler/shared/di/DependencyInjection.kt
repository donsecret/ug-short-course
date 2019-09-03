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
import dev.ugscheduler.shared.datasource.local.LocalDatabase
import dev.ugscheduler.shared.datasource.remote.RemoteDataSource
import dev.ugscheduler.shared.repository.AppRepository
import dev.ugscheduler.shared.util.Constants
import dev.ugscheduler.shared.util.prefs.UserSharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

// Load all modules declared below from here
fun loadAppModules() = mutableListOf(firebaseModule, appPrefsModule, datasourceModule)

private val firebaseModule = module {
    single { FirebaseApp.initializeApp(androidContext()) }
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance().reference.child(Constants.BUCKET_NAME) }
    single { FirebaseMessaging.getInstance() }
}

private val appPrefsModule = module {
    single { UserSharedPreferences.getInstance(androidContext()) }
    single { LocalDatabase.get(androidContext()) }
    single { get<LocalDatabase>().studentDao() }
    single { get<LocalDatabase>().courseDao() }
    single { get<LocalDatabase>().feedbackDao() }
    single { get<LocalDatabase>().facilitatorDao() }
}

private val datasourceModule = module {
    factory { RemoteDataSource(get(), get()) }
    factory { LocalDataSource(get(), get(), get(), get(), get()) }
    single { AppRepository(get(), get(), get()) }
}


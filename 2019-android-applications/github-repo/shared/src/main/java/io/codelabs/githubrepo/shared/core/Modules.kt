/*
 * Copyright (c) 2019. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package io.codelabs.githubrepo.shared.core

import com.google.firebase.auth.FirebaseAuth
import io.codelabs.githubrepo.shared.core.prefs.AppSharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun loadModules() = mutableListOf(firebaseModule, networkModule, prefsModule)

private val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
}

private val networkModule = module {
    // todo: network modules
}

private val prefsModule = module {
    single { AppSharedPreferences.get(androidContext()) }
}
package io.codelabs.ugcloudchat.core

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.codelabs.ugcloudchat.model.database.UGCloudChatDB
import io.codelabs.ugcloudchat.model.preferences.UserSharedPreferences
import io.codelabs.ugcloudchat.model.provider.LocalContactsProvider
import io.codelabs.ugcloudchat.viewmodel.repository.ChatRepository
import io.codelabs.ugcloudchat.viewmodel.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Room database module
 */
val roomModule = module {
    single { UGCloudChatDB.getInstance(androidContext()).dao() }
}


/**
 * User settings shared preferences module
 */
val prefsModule = module {
    single { UserSharedPreferences.getInstance(androidContext()) }
}


/**
 * Firebase SDK module
 */
val firebaseModule = module {
    single { FirebaseFirestore.getInstance() }

    single { FirebaseAuth.getInstance() }
}

/**
 * Contact provider module
 */
val providerModule = module {
    single { LocalContactsProvider.getInstance() }
}

val androidModule = module {
    /*Repositories*/
    single { UserRepository.getInstance(get(), get()) }
    single { ChatRepository.getInstance(get(), get()) }

    /*Factories*/
    

    /*ViewModels*/
}
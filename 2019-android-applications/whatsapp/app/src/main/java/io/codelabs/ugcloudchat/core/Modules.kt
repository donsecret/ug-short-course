package io.codelabs.ugcloudchat.core

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.codelabs.ugcloudchat.viewmodel.LocalContactsProvider
import org.koin.dsl.module

/**
 * Room database module
 */
val roomModule = module {

}


/**
 * User settings shared preferences module
 */
val prefsModule = module {

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
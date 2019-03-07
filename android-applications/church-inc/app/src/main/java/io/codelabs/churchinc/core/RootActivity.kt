package io.codelabs.churchinc.core

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import io.codelabs.churchinc.core.datasource.ChurchIncDao
import io.codelabs.churchinc.util.debugLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * Root activity for all Activities in this application
 */
abstract class RootActivity : AppCompatActivity() {

    // INJECT FIREBASE SDK HERE
    private val app: FirebaseApp by inject { parametersOf(application as ChurchIncApplication) }
    val firestore: FirebaseFirestore by inject()
    val storage: FirebaseStorage by inject()
    val auth: FirebaseAuth by inject()

    // ROOM DATABASE DAO
    val dao: ChurchIncDao by inject { parametersOf(application as ChurchIncApplication) }

    // COROUTINES
    private val job = Job()
    // BACKGROUND TASKS
    val ioScope = CoroutineScope(Dispatchers.IO + job)
    // FOREGROUND TASKS
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onStart() {
        super.onStart()
        debugLog("Firebase Application is initialized as : ${app.name}")
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
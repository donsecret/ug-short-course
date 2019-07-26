package io.codelabs.ugcloudchat.view

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.codelabs.ugcloudchat.model.WhatsappUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class BaseActivity : AppCompatActivity() {

    /**
     * Background Thread
     */
    val ioScope = CoroutineScope(Dispatchers.IO)

    /**
     * Main Thread
     */
    val uiScope = CoroutineScope(Dispatchers.Main)

    /**
     * Firebase Auth instance
     */
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    /**
     * Firestore instance
     */
    val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    /**
     * Collection of all user documents
     */
    val userCollection = db.collection("users")


    /**
     * Document of single user
     */
    fun getUserByUID(uid: String): WhatsappUser? {
        return Tasks.await(userCollection.document(uid).get()).toObject(WhatsappUser::class.java)
    }

}
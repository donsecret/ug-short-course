package io.codelabs.ugcloudchat.model.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import io.codelabs.ugcloudchat.model.database.UGCloudChatDB
import io.codelabs.ugcloudchat.util.debugThis
import io.codelabs.ugcloudchat.viewmodel.LocalContactsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Worker to load all contacts on the user's device and compare with the online data set
 */
class LocalContactWorker(private val context: Context, private val params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    private val dao by lazy { UGCloudChatDB.getInstance(context).dao() }
    private val contactProvider by lazy { LocalContactsProvider.getInstance() }


    private suspend fun getAndStoreContacts() = withContext(Dispatchers.IO) {
        try {
            contactProvider.getLocalContacts(context) { contacts ->
                debugThis("Get local contacts returned a list of ${contacts?.size ?: 0} contacts")
            }
        } catch (ex: Exception) {
            // Catch any errors that may occur
            debugThis("An error occurred while retrieving your contacts' list: ${ex.localizedMessage}")
        }
    }

    override suspend fun doWork(): Result {
        getAndStoreContacts()
        return Result.success()
    }
}
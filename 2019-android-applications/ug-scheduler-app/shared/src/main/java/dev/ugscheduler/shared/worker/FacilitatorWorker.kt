/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.shared.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.ugscheduler.shared.data.Facilitator
import dev.ugscheduler.shared.datasource.local.FacilitatorDao
import dev.ugscheduler.shared.datasource.local.LocalDatabase
import dev.ugscheduler.shared.datasource.remote.facilitatorDocument
import dev.ugscheduler.shared.util.debugger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

/**
 * Worker for handling [Facilitator] information retrieval from the remote database to the local database
 */
class FacilitatorWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    private val dao: FacilitatorDao by lazy { LocalDatabase.get(context).facilitatorDao() }
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override suspend fun doWork(): Result {
        debugger("De-serializing and adding facilitators")

        // Get all facilitators and deserialize
        val facilitators = getFacilitators(applicationContext)

        // Needed to be called on the background thread
        withContext(Dispatchers.IO) {
            // Store locally
            dao.insertAll(facilitators)

            // Forward all course details to remote data source
            for (facilitator in facilitators) {
                try {
                    Tasks.await(
                        firestore.facilitatorDocument(facilitator.id).set(
                            facilitator,
                            SetOptions.merge()
                        )
                    )
                } catch (e: Exception) {
                    debugger("Adding courses exception: ${e.localizedMessage}")
                }
            }
        }
        return Result.success()
    }

    private suspend fun getFacilitators(context: Context): MutableList<Facilitator> =
        withContext(IO) {
            try {
                Gson().fromJson<List<Facilitator>>(InputStreamReader(context.assets.open("facilitators.json")),
                    object : TypeToken<List<Facilitator>>() {}.type
                ).toMutableList()
            } catch (ex: Exception) {
                debugger(ex.localizedMessage)
                mutableListOf<Facilitator>()
            }
        }
}
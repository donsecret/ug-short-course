package dev.ugscheduler.shared.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dev.ugscheduler.shared.util.debugger

/**
 * Worker for handling [Course] information retrieval from the remote database to the local database
 */
class CourseWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    // todo: get remote and local data sources

    override suspend fun doWork(): Result {
        // todo: Get all courses from the database
        debugger("Staring work for online courses")
        return Result.success()
    }
}
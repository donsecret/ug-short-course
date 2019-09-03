/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.shared.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dev.ugscheduler.shared.data.Course
import dev.ugscheduler.shared.data.Facilitator
import dev.ugscheduler.shared.data.Feedback
import dev.ugscheduler.shared.data.Student
import dev.ugscheduler.shared.util.Constants
import dev.ugscheduler.shared.worker.CourseWorker


@Database(
    entities = [Student::class, Facilitator::class, Course::class, Feedback::class],
    version = Constants.DB_VERSION,
    exportSchema = true
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao
    abstract fun courseDao(): CourseDao
    abstract fun feedbackDao(): FeedbackDao
    abstract fun facilitatorDao(): FacilitatorDao

    companion object {
        @Volatile
        private var instance: LocalDatabase? = null

        fun get(context: Context): LocalDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context,
                LocalDatabase::class.java,
                Constants.LOCAL_DB_NAME
            )
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        // Set constraints to start job only when there is internet connection
                        val constraints = Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()

                        // Set a one-time request to download all courses and store them locally
                        with(WorkManager.getInstance(context)) {
                            enqueue(
                                OneTimeWorkRequestBuilder<CourseWorker>().setConstraints(
                                    constraints
                                ).build()
                            )
                        }
                    }
                })
                .build().also { instance = it }
        }
    }
}


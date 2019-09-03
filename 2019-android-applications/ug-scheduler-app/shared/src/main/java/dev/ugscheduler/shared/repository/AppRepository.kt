/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.shared.repository

import android.content.Context
import androidx.lifecycle.LiveData
import dev.ugscheduler.shared.data.*
import dev.ugscheduler.shared.datasource.local.LocalDataSource
import dev.ugscheduler.shared.datasource.remote.RemoteDataSource
import dev.ugscheduler.shared.util.prefs.UserSharedPreferences

/**
 * Uses both data sources to know where to fetch which data
 */
class AppRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val prefs: UserSharedPreferences
) {
    fun getAllCourses(context: Context, refresh: Boolean): MutableList<Course> {
        return if (refresh) remoteDataSource.getAllCourses(context).apply {
            localDataSource.addCourses(
                this
            )
        }
        else localDataSource.getAllCourses(context)
    }

    fun getFacilitators(refresh: Boolean): MutableList<Facilitator> {
        return if (refresh) remoteDataSource.getFacilitators().apply {
            localDataSource.addFacilitators(
                this
            )
        }
        else localDataSource.getFacilitators()
    }

    fun getFacilitatorById(id: String, refresh: Boolean): LiveData<Facilitator> {
        return if (refresh) remoteDataSource.getFacilitatorById(id).apply {
            localDataSource.addFacilitator(
                this.value
            )
        }
        else localDataSource.getFacilitatorById(id)
    }

    fun enrolStudent(enrolment: Enrolment) =
        remoteDataSource.enrolStudent(enrolment).apply { localDataSource.enrolStudent(enrolment) }

    fun getCurrentStudent(refresh: Boolean): LiveData<Student> {
        return if (refresh) remoteDataSource.getCurrentStudent(prefs.uid).apply {
            localDataSource.addStudent(
                this.value
            )
        }
        else localDataSource.getCurrentStudent(prefs.uid)
    }

    fun getCurrentFacilitator(refresh: Boolean): LiveData<Facilitator> {
        return if (refresh) remoteDataSource.getCurrentFacilitator(prefs.uid).apply {
            localDataSource.addFacilitator(
                this.value
            )
        }
        else localDataSource.getCurrentFacilitator(prefs.uid)
    }

    fun getMyCourses(refresh: Boolean): MutableList<Course> {
        return if (refresh) remoteDataSource.getMyCourses(prefs.uid).apply {
            localDataSource.addMyCourses(
                this
            )
        }
        else localDataSource.getMyCourses(prefs.uid)
    }

    fun getCoursesForFacilitator(facilitatorId: String, refresh: Boolean): MutableList<Course> {
        return if (refresh) remoteDataSource.getCoursesForFacilitator(facilitatorId).apply {
            localDataSource.addCourses(
                this
            )
        }
        else localDataSource.getCoursesForFacilitator(facilitatorId)
    }

    fun sendFeedback(feedback: Feedback) {
        remoteDataSource.sendFeedback(feedback).apply { localDataSource.sendFeedback(feedback) }
    }

    fun getStudentById(studentId: String, refresh: Boolean): LiveData<Student> {
        return if (refresh) remoteDataSource.getStudentById(studentId).apply {
            localDataSource.addStudent(
                this.value
            )
        }
        else localDataSource.getStudentById(studentId)
    }

    fun loginStudent(student: Student?) {
        localDataSource.addStudent(student).apply { remoteDataSource.addStudent(student) }
        prefs.login(student?.id)
    }

    fun loginFacilitator(facilitator: Facilitator?) {
        localDataSource.addFacilitator(facilitator)
            .apply { remoteDataSource.addFacilitator(facilitator) }
        prefs.login(facilitator?.id)
    }

    fun logout() {
        remoteDataSource.auth.signOut()
        prefs.logout()
    }

    fun invalidateLocalCaches() = localDataSource.clearDatabase()
}
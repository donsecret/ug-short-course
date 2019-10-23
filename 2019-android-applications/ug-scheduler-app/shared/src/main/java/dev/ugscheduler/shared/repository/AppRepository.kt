/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.shared.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.ugscheduler.shared.data.*
import dev.ugscheduler.shared.datasource.local.LocalDataSource
import dev.ugscheduler.shared.datasource.remote.RemoteDataSource
import dev.ugscheduler.shared.util.prefs.UserSharedPreferences

interface Repository {
    fun getAllCourses(context: Context, refresh: Boolean): MutableList<Course>
    fun getFacilitators(refresh: Boolean): MutableList<Facilitator>
    fun getFacilitatorById(id: String, refresh: Boolean): LiveData<Facilitator>
    fun enrolStudent(enrolment: Enrolment)
    fun getCurrentStudent(refresh: Boolean): LiveData<Student>
    fun getCurrentFacilitator(refresh: Boolean): LiveData<Facilitator>
    fun getMyCourses(refresh: Boolean): MutableList<Course>
    fun getCoursesForFacilitator(facilitatorId: String, refresh: Boolean): MutableList<Course>
    fun sendFeedback(feedback: Feedback)
    fun getStudentById(studentId: String, refresh: Boolean): LiveData<Student>
    fun loginStudent(student: Student?)
    fun loginFacilitator(facilitator: Facilitator?)
    fun logout()
    fun invalidateLocalCaches()
}

/**
 * Uses both data sources to know where to fetch which data
 */
class AppRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val prefs: UserSharedPreferences
) : Repository {
    override fun getAllCourses(context: Context, refresh: Boolean): MutableList<Course> {
        return if (refresh) remoteDataSource.getAllCourses(context).apply {
            localDataSource.addCourses(
                this
            )
        }
        else localDataSource.getAllCourses(context)
    }

    override fun getFacilitators(refresh: Boolean): MutableList<Facilitator> {
        return if (refresh) remoteDataSource.getFacilitators().apply {
            localDataSource.addFacilitators(
                this
            )
        }
        else localDataSource.getFacilitators()
    }

    override fun getFacilitatorById(id: String, refresh: Boolean): LiveData<Facilitator> {
        val liveFacilitator = MutableLiveData<Facilitator>()
        if (refresh) {
            remoteDataSource.getFacilitatorById(id).observeForever {
                if (it != null) {
                    liveFacilitator.postValue(it)
                    localDataSource.addFacilitator(it)
                }
            }
        } else {
            localDataSource.getFacilitatorById(id).observeForever {
                liveFacilitator.postValue(it)
            }
        }
        return liveFacilitator
    }

    override fun enrolStudent(enrolment: Enrolment) =
        remoteDataSource.enrolStudent(enrolment).apply { localDataSource.enrolStudent(enrolment) }

    override fun getCurrentStudent(refresh: Boolean): LiveData<Student> {
        val liveStudent = MutableLiveData<Student>()
        if (refresh) remoteDataSource.getCurrentStudent(prefs.uid).observeForever { student ->
            if (student != null) {
                localDataSource.addStudent(student)
            }
            liveStudent.postValue(student)
        }
        else {
            localDataSource.getCurrentStudent(prefs.uid).observeForever { student ->
                liveStudent.postValue(student)
            }
        }
        return liveStudent
    }

    override fun getCurrentFacilitator(refresh: Boolean): LiveData<Facilitator> {
        val liveData = MutableLiveData<Facilitator>()
        if (refresh) remoteDataSource.getCurrentFacilitator(prefs.uid).observeForever { facilitator ->
            if (facilitator != null) {
                localDataSource.addFacilitator(facilitator)
            }
            liveData.postValue(facilitator)
        }
        else {
            localDataSource.getCurrentFacilitator(prefs.uid).observeForever { facilitator ->
                liveData.postValue(facilitator)
            }
        }
        return liveData
    }

    override fun getMyCourses(refresh: Boolean): MutableList<Course> {
        return if (refresh) remoteDataSource.getMyCourses(prefs.uid).apply {
            localDataSource.addMyCourses(
                this
            )
        }
        else localDataSource.getMyCourses(prefs.uid)
    }

    override fun getCoursesForFacilitator(
        facilitatorId: String,
        refresh: Boolean
    ): MutableList<Course> {
        return if (refresh) remoteDataSource.getCoursesForFacilitator(facilitatorId).apply {
            localDataSource.addCourses(
                this
            )
        }
        else localDataSource.getCoursesForFacilitator(facilitatorId)
    }

    override fun sendFeedback(feedback: Feedback) {
        remoteDataSource.sendFeedback(feedback).apply { localDataSource.sendFeedback(feedback) }
    }

    override fun getStudentById(studentId: String, refresh: Boolean): LiveData<Student> {
        return if (refresh) remoteDataSource.getStudentById(studentId).apply {
            localDataSource.addStudent(
                this.value
            )
        }
        else localDataSource.getStudentById(studentId)
    }

    override fun loginStudent(student: Student?) {
        localDataSource.addStudent(student).apply { remoteDataSource.addStudent(student) }
        prefs.login(student?.id)
    }

    override fun loginFacilitator(facilitator: Facilitator?) {
        localDataSource.addFacilitator(facilitator)
            .apply { remoteDataSource.addFacilitator(facilitator) }
        prefs.login(facilitator?.id)
    }

    override fun logout() {
        remoteDataSource.auth.signOut()
        prefs.logout()
    }

    override fun invalidateLocalCaches() = localDataSource.clearDatabase()
}
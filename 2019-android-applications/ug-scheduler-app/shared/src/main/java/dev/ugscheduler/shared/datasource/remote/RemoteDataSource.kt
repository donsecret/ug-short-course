/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.shared.datasource.remote

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.ugscheduler.shared.data.Course
import dev.ugscheduler.shared.data.Facilitator
import dev.ugscheduler.shared.data.Feedback
import dev.ugscheduler.shared.data.Student
import dev.ugscheduler.shared.datasource.DataSource

/**
 * Remote data source
 */
class RemoteDataSource constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : DataSource {
    override fun sendFeedback(feedback: Feedback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStudentById(studentId: String): LiveData<Student> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFacilitators(): MutableList<Facilitator> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFacilitatorById(id: String): LiveData<Facilitator> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enrolStudent(studentId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrentStudent(id: String): LiveData<Student> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrentFacilitator(id: String): LiveData<Facilitator> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMyCourses(studentId: String): MutableList<Course> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCoursesForFacilitator(facilitatorId: String): MutableList<Course> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
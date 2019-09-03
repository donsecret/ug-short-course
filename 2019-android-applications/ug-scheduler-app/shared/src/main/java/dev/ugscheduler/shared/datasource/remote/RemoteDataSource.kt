/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.shared.datasource.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dev.ugscheduler.shared.data.*
import dev.ugscheduler.shared.datasource.DataSource
import dev.ugscheduler.shared.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Remote data source
 */
class RemoteDataSource constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth  // Will be used in future updates
) : DataSource {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun sendFeedback(feedback: Feedback) {
        ioScope.launch {
            Tasks.await(
                firestore.feedbackDocument(feedback.id).set(
                    feedback,
                    SetOptions.merge()
                )
            )
        }
    }

    override fun getStudentById(studentId: String): LiveData<Student> {
        val liveData = MutableLiveData<Student>()
        ioScope.launch {
            val student = Tasks.await(firestore.studentDocument(studentId).get())
                .toObject(Student::class.java)
            if (student != null) liveData.value = student
        }
        return liveData
    }

    override fun getFacilitators(): MutableList<Facilitator> {
        val facilitators = mutableListOf<Facilitator>()
        ioScope.launch {
            val result = Tasks.await(firestore.collection(Constants.FACILITATORS).get())
                .toObjects(Facilitator::class.java)
            facilitators.addAll(result)
        }
        return facilitators
    }

    override fun getFacilitatorById(id: String): LiveData<Facilitator> {
        val liveData = MutableLiveData<Facilitator>()
        ioScope.launch {
            val facilitator = Tasks.await(firestore.facilitatorDocument(id).get())
                .toObject(Facilitator::class.java)
            if (facilitator != null) liveData.value = facilitator
        }
        return liveData
    }

    override fun enrolStudent(enrolment: Enrolment) {
        ioScope.launch {
            Tasks.await(
                firestore.collection(Constants.ENROLMENTS).document().set(
                    enrolment,
                    SetOptions.merge()
                )
            )
        }
    }

    override fun getCurrentStudent(id: String): LiveData<Student> {
        val liveData = MutableLiveData<Student>()
        ioScope.launch {
            val student =
                Tasks.await(firestore.studentDocument(id).get()).toObject(Student::class.java)
            if (student != null) liveData.value = student
        }
        return liveData
    }

    override fun getCurrentFacilitator(id: String): LiveData<Facilitator> {
        val liveData = MutableLiveData<Facilitator>()
        ioScope.launch {
            val fac =
                Tasks.await(firestore.facilitatorDocument(id).get())
                    .toObject(Facilitator::class.java)
            if (fac != null) liveData.value = fac
        }
        return liveData
    }

    override fun getMyCourses(studentId: String): MutableList<Course> {
        val courses = mutableListOf<Course>()
        ioScope.launch {
            val results =
                Tasks.await(firestore.studentDocument(studentId).collection(Constants.COURSES).get())
                    .toObjects(Course::class.java)
            courses.addAll(results)
        }
        return courses
    }

    override fun getCoursesForFacilitator(facilitatorId: String): MutableList<Course> {
        val courses = mutableListOf<Course>()
        ioScope.launch {
            val results =
                Tasks.await(
                    firestore.collection(Constants.COURSES)
                        .whereEqualTo("facilitator", facilitatorId)
                        .get()
                )
                    .toObjects(Course::class.java)
            courses.addAll(results)
        }
        return courses
    }

}
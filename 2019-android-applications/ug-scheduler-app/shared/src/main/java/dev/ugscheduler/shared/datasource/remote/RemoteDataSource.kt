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
import dev.ugscheduler.shared.util.debugger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Remote data source
 */
class RemoteDataSource constructor(
    private val firestore: FirebaseFirestore,
    val auth: FirebaseAuth
) : DataSource {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun sendFeedback(feedback: Feedback) {
        ioScope.launch {
            try {
                Tasks.await(
                    firestore.feedbackDocument(feedback.id).set(
                        feedback,
                        SetOptions.merge()
                    )
                )
            } catch (e: Exception) {
                debugger(e.localizedMessage)
            }
        }
    }

    override fun getStudentById(studentId: String): LiveData<Student> {
        val liveData = MutableLiveData<Student>()
        ioScope.launch {
            try {
                val student = Tasks.await(firestore.studentDocument(studentId).get())
                    .toObject(Student::class.java)
                if (student != null) liveData.postValue(student)
            } catch (e: Exception) {
                debugger(e.localizedMessage)
            }
        }
        return liveData
    }

    override fun getFacilitators(): MutableList<Facilitator> {
        val facilitators = mutableListOf<Facilitator>()
        ioScope.launch {
            try {
                val result = Tasks.await(firestore.collection(Constants.FACILITATORS).get())
                    .toObjects(Facilitator::class.java)
                facilitators.addAll(result)
            } catch (e: Exception) {
                debugger(e.localizedMessage)
            }
        }
        return facilitators
    }

    override fun getFacilitatorById(id: String): LiveData<Facilitator> {
        val liveData = MutableLiveData<Facilitator>()
        ioScope.launch {
            try {
                val facilitator = Tasks.await(firestore.facilitatorDocument(id).get())
                    .toObject(Facilitator::class.java)
                if (facilitator != null) liveData.postValue(facilitator)
            } catch (e: Exception) {
                debugger(e.localizedMessage)
            }
        }
        return liveData
    }

    override fun enrolStudent(enrolment: Enrolment) {
        ioScope.launch {
            try {
                Tasks.await(
                    firestore.collection(Constants.ENROLMENTS).document().set(
                        enrolment,
                        SetOptions.merge()
                    )
                )
            } catch (e: Exception) {
                debugger(e.localizedMessage)
            }
        }
    }

    override fun getCurrentStudent(id: String): LiveData<Student> {
        val liveData = MutableLiveData<Student>()
        ioScope.launch {
            try {
                val student =
                    Tasks.await(firestore.studentDocument(id).get()).toObject(Student::class.java)
                liveData.postValue(student)
            } catch (e: Exception) {
                debugger(e.localizedMessage)
                liveData.postValue(null)
            }
        }
        return liveData
    }

    override fun getCurrentFacilitator(id: String): LiveData<Facilitator> {
        val liveData = MutableLiveData<Facilitator>()
        ioScope.launch {
            try {
                val fac =
                    Tasks.await(firestore.facilitatorDocument(id).get())
                        .toObject(Facilitator::class.java)
                liveData.postValue(fac)
            } catch (e: Exception) {
                liveData.postValue(null)
                debugger(e.localizedMessage)
            }
        }
        return liveData
    }

    override fun getMyCourses(studentId: String): MutableList<Course> {
        val courses = mutableListOf<Course>()
        ioScope.launch {
            try {
                val results =
                    Tasks.await(firestore.studentDocument(studentId).collection(Constants.COURSES).get())
                        .toObjects(Course::class.java)
                courses.addAll(results)
            } catch (e: Exception) {
                debugger(e.localizedMessage)
            }
        }
        return courses
    }

    override fun getCoursesForFacilitator(facilitatorId: String): MutableList<Course> {
        val courses = mutableListOf<Course>()
        ioScope.launch {
            try {
                val results =
                    Tasks.await(
                        firestore.collection(Constants.COURSES)
                            .whereEqualTo("facilitator", facilitatorId)
                            .get()
                    )
                        .toObjects(Course::class.java)
                courses.addAll(results)
            } catch (e: Exception) {
                debugger(e.localizedMessage)
            }
        }
        return courses
    }

    fun addStudent(student: Student?) {
        if (student == null) return
        ioScope.launch {
            try {
                firestore.runTransaction { t ->
                    val oldStudent =
                        t.get(firestore.studentDocument(student.id))
                    if (oldStudent.exists()) {
                        val data = oldStudent.toObject(Student::class.java)
                        debugger("Student from database: $data")
                    } else {
                        t.set(firestore.studentDocument(student.id), student, SetOptions.merge())
                    }
                    null
                }
            } catch (e: Exception) {
                debugger(e.localizedMessage)
            }
        }
    }

    fun addFacilitator(facilitator: Facilitator?) {
        if (facilitator == null) return
        ioScope.launch {
            try {
                Tasks.await(
                    firestore.facilitatorDocument(facilitator.id).set(
                        facilitator,
                        SetOptions.merge()
                    )
                )
            } catch (e: Exception) {
                debugger(e.localizedMessage)
            }
        }
    }

}
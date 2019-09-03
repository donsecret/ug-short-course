/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.shared.datasource.local

import androidx.lifecycle.LiveData
import dev.ugscheduler.shared.data.*
import dev.ugscheduler.shared.datasource.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Local data source
 */
class LocalDataSource constructor(
    val database: LocalDatabase,
    private val courseDao: CourseDao,
    private val studentDao: StudentDao,
    private val feedbackDao: FeedbackDao,
    private val facilitatorDao: FacilitatorDao
) : DataSource {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    
    override fun sendFeedback(feedback: Feedback) = feedbackDao.insert(feedback)

    override fun getStudentById(studentId: String): LiveData<Student> =
        studentDao.getStudent(studentId)

    override fun getFacilitators(): MutableList<Facilitator> = facilitatorDao.getAllFacilitators()

    override fun getFacilitatorById(id: String): LiveData<Facilitator> =
        facilitatorDao.getFacilitatorById(id)

    override fun enrolStudent(enrolment: Enrolment) = TODO("Not supported yet")

    override fun getCurrentStudent(id: String): LiveData<Student> = studentDao.getStudent(id)

    override fun getCurrentFacilitator(id: String): LiveData<Facilitator> =
        facilitatorDao.getFacilitatorById(id)

    override fun getMyCourses(studentId: String): MutableList<Course> = TODO("not supported yet")

    override fun getCoursesForFacilitator(facilitatorId: String): MutableList<Course> =
        courseDao.getCoursesForFacilitator(facilitatorId)

    fun addFacilitators(facilitators: MutableList<Facilitator>) {
        ioScope.launch {
            facilitatorDao.insertAll(facilitators)
        }
    }

    fun addCourses(courses: MutableList<Course>) {
        ioScope.launch {
            courseDao.insertAll(courses)
        }
    }

    fun addFacilitator(facilitator: Facilitator?) {
        ioScope.launch {
            if (facilitator != null) facilitatorDao.insert(facilitator)
        }
    }

    fun addStudent(student: Student?) {
        ioScope.launch {
            if (student != null) studentDao.insert(student)
        }
    }

    fun addMyCourses(courses: MutableList<Course>) {
        TODO("Add my courses")
    }

    // Clear local database
    fun clearDatabase() = database.clearAllTables()

}
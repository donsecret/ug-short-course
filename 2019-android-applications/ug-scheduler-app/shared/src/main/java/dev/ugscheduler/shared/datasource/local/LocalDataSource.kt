/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.shared.datasource.local

import androidx.lifecycle.LiveData
import dev.ugscheduler.shared.data.Course
import dev.ugscheduler.shared.data.Facilitator
import dev.ugscheduler.shared.data.Feedback
import dev.ugscheduler.shared.data.Student
import dev.ugscheduler.shared.datasource.DataSource

/**
 * Local data source
 */
class LocalDataSource constructor(
    private val courseDao: CourseDao,
    private val studentDao: StudentDao,
    private val feedbackDao: FeedbackDao,
    private val facilitatorDao: FacilitatorDao
) : DataSource {
    override fun sendFeedback(feedback: Feedback) = feedbackDao.insert(feedback)

    override fun getStudentById(studentId: String): LiveData<Student> =
        studentDao.getStudent(studentId)

    override fun getFacilitators(): MutableList<Facilitator> = facilitatorDao.getAllFacilitators()

    override fun getFacilitatorById(id: String): LiveData<Facilitator> =
        facilitatorDao.getFacilitatorById(id)

    override fun enrolStudent(studentId: String) = TODO("Not supported yet")

    override fun getCurrentStudent(id: String): LiveData<Student> = studentDao.getStudent(id)

    override fun getCurrentFacilitator(id: String): LiveData<Facilitator> =
        facilitatorDao.getFacilitatorById(id)

    override fun getMyCourses(studentId: String): MutableList<Course> = TODO("not supported yet")

    override fun getCoursesForFacilitator(facilitatorId: String): MutableList<Course> =
        TODO("Not supported yet ")
}
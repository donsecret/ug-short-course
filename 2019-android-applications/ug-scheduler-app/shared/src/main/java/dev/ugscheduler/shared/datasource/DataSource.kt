/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.shared.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import dev.ugscheduler.shared.data.Course
import dev.ugscheduler.shared.data.Facilitator
import dev.ugscheduler.shared.data.Feedback
import dev.ugscheduler.shared.data.Student
import dev.ugscheduler.shared.util.deserializer.getCourses

/**
 * Base data source
 */
interface DataSource {
    fun getAllCourses(context: Context): MutableList<Course> = getCourses(context)
    fun getFacilitators(): MutableList<Facilitator>
    fun getFacilitatorById(id: String): LiveData<Facilitator>
    fun enrolStudent(studentId: String)
    fun getCurrentStudent(id: String): LiveData<Student>
    fun getCurrentFacilitator(id: String): LiveData<Facilitator>
    fun getMyCourses(studentId: String): MutableList<Course>
    fun getCoursesForFacilitator(facilitatorId: String): MutableList<Course>
    fun sendFeedback(feedback: Feedback)
    fun getStudentById(studentId: String): LiveData<Student>
}
package dev.ugscheduler.domain.repository

import dev.ugscheduler.domain.model.Course

/**
 * Course repository
 */
interface CourseRepository {
    fun getCourses(refresh: Boolean): MutableList<Course>
    fun getCourse(courseId: String, refresh: Boolean): Course
}
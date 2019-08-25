package dev.ugscheduler.domain.datasource

import dev.ugscheduler.domain.model.Course

interface CourseDataSource {
    fun getCourses(refresh: Boolean): MutableList<Course>
    fun getCourse(courseId: String, refresh: Boolean): Course
}
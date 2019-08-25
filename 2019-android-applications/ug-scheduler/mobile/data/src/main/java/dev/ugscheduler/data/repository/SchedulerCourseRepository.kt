package dev.ugscheduler.data.repository

import dev.ugscheduler.data.datasource.SchedulerCourseDataSource
import dev.ugscheduler.domain.model.Course
import dev.ugscheduler.domain.repository.CourseRepository

// TODO: INJECT THIS as [CourseRepository]
class SchedulerCourseRepository constructor(private val dataSource: SchedulerCourseDataSource) :
    CourseRepository {
    override fun getCourses(refresh: Boolean): MutableList<Course> =
        dataSource.getCourses(refresh)

    override fun getCourse(courseId: String, refresh: Boolean): Course =
        dataSource.getCourse(courseId, refresh)
}
package dev.ugscheduler.data.datasource

import dev.ugscheduler.domain.datasource.CourseDataSource
import dev.ugscheduler.domain.model.Course

// TODO: INJECT THIS AS [CourseDataSource]
open class SchedulerCourseDataSource constructor(/*TODO: add remote and local data sources here*/) :
    CourseDataSource {
    override fun getCourses(refresh: Boolean): MutableList<Course> {
        TODO()
    }

    override fun getCourse(courseId: String, refresh: Boolean): Course {
        TODO()
    }
}
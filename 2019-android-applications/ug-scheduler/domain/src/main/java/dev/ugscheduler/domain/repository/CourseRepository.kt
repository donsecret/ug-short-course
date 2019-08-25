package dev.ugscheduler.domain.repository

import dev.ugscheduler.domain.model.Course

/**
 * Course repository
 */
interface CourseRepository {
    fun get(refresh: Boolean): MutableList<Course>
}
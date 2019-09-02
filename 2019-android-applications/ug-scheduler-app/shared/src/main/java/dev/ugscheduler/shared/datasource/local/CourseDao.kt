package dev.ugscheduler.shared.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import dev.ugscheduler.shared.data.Course

@Dao
interface CourseDao : BaseDao<Course> {

    @Query("select * from courses where id = :id")
    fun getCourseDetails(id: String): LiveData<Course>

    @Query("select * from courses order by name desc")
    fun getAllCourses(): LiveData<MutableList<Course>>

}
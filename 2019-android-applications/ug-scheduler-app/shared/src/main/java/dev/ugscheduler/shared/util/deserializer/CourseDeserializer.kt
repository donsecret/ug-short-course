package dev.ugscheduler.shared.util.deserializer

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dev.ugscheduler.shared.data.Course
import dev.ugscheduler.shared.util.debugger
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

fun Any.getCourses(context: Context): MutableList<Course> {
    val courses = ArrayList<Course>(0)
    val gson = GsonBuilder().setPrettyPrinting().create()
    val type = object : TypeToken<List<Course>>() {}.type
    try {
        val courseList = gson.fromJson<List<Course>>(
            InputStreamReader(context.assets.open("courses.json")),
            type
        )
        courses.addAll(courseList)
    } catch (e: IOException) {
        debugger(e.localizedMessage)
    }
    return courses
}
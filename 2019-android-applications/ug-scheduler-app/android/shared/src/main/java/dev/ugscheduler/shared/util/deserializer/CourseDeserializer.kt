/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.shared.util.deserializer

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.ugscheduler.shared.data.Course
import java.io.InputStreamReader

fun getCourses(context: Context): MutableList<Course> =
    context.fromJson("courses.json")

fun Context.fromJson(fileName: String): MutableList<Course> {
    return Gson().fromJson<List<Course>>(
        InputStreamReader(assets.open(fileName)),
        object : TypeToken<List<Course>>() {}.type
    ).toMutableList()
}
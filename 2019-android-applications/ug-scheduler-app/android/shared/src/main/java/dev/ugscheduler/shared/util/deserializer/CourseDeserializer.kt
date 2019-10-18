/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.shared.util.deserializer

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dev.ugscheduler.shared.data.Course
import dev.ugscheduler.shared.util.debugger
import java.io.FileInputStream
import java.io.FileReader
import java.io.InputStream
import java.io.InputStreamReader

fun Any.getCourses(context: Context): MutableList<Course> {
    try {
        debugger(context.fromJson<Course>("courses.json"))
    } catch (e: Exception) {
        debugger(e.localizedMessage)
    }
    return mutableListOf()
}

fun <T> Context.fromJson(fileName: String): MutableList<T> {
    return GsonBuilder().setPrettyPrinting().create()
        .fromJson(InputStreamReader(assets.open(fileName)), object : TypeToken<MutableList<T>>() {}.type)
}
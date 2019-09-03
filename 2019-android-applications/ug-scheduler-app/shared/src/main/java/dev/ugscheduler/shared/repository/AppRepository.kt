/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.shared.repository

import android.content.Context
import dev.ugscheduler.shared.data.Course
import dev.ugscheduler.shared.datasource.local.LocalDataSource
import dev.ugscheduler.shared.datasource.remote.RemoteDataSource
import dev.ugscheduler.shared.util.prefs.UserSharedPreferences

/**
 * Uses both data sources to know where to fetch which data
 */
class AppRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val prefs: UserSharedPreferences
) {
    fun getAllCourses(context: Context): MutableList<Course> =
        localDataSource.getAllCourses(context)


}
/*
 * Copyright (c) 2019. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package io.codelabs.githubrepo.shared.datasource.remote

import io.codelabs.githubrepo.shared.BuildConfig

interface GitHubService {
    companion object {
        const val BASE_URL = BuildConfig.API_BASE_URL
    }
}

object RepoAPI {

    // Create service for making calls to GitHub API
    fun createService(): GitHubService {
        TODO()
    }


}
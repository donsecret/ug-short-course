/*
 * Copyright (c) 2019. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package io.codelabs.githubrepo.shared.datasource

interface DataSource {
    fun getAllRepos()
    fun requestUserIdentity(uid: String)
}
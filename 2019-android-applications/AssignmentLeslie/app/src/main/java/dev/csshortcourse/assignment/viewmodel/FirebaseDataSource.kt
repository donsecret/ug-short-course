package dev.csshortcourse.assignment.viewmodel

import dev.csshortcourse.assignment.model.User

/**
 *   ARCHITECTURE OF THE APPLICATION
 *   *******************************
 *   Send request
 *   VIEW => VIEW-MODEL => REPOSITORY => DATA-SOURCE
 *
 *   Receive response
 *   DATA-SOURCE => REPOSITORY => VIEW-MODEL => VIEW
 *
 *   This class is responsible for handling actions to perform the following functions:
 *   1. Login user
 *   2. Create user account
 *   3. Fetch user information from database in real-time
 */
class FirebaseDataSource {
    private val auth by lazy { Any() }
    private val database by lazy { Any() }

    fun login(email: String, password: String, callback: Callback<User>) {

    }
}
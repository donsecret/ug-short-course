package io.codelabs.ugcloudchat

/**
 * Chat data model
 */
data class Chat(
    val key: String,
    val message: String,
    val sender: String,
    val recipient: String,
    val timestamp: Long = System.currentTimeMillis()
) {

    constructor() : this("", "", "", "")
}
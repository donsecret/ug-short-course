package io.codelabs.ugcloudchat

/**
 * User data model
 */
data class WhatsappUser(
    val uid: String,
    val phone: String,
    var timestamp: Long = System.currentTimeMillis()
) {

    /**
     * Needed by the Firestore SDK for serialization
     */
    constructor() : this("", "")

}
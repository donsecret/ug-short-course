package io.codelabs.ugcloudchat


/**
 * Local contact data model
 */
data class LocalContact(
    val id: Long,
    val phone: String,
    var displayName: String? = null,
    var lookupKey: String? = null,
    var photoUri: String? = null,
    var thumbNailUri: String? = null
)
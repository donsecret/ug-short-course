package io.codelabs.ugcloudchat.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * User data model
 */
@Entity(tableName = "users")
data class WhatsappUser(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val uid: String,
    val phone: String?,
    var displayName: String? = null,
    var photoUri: String? = null,
    var thumbNailUri: String? = null,
    var timestamp: Long = System.currentTimeMillis()
) {

    /**
     * Needed by the Firestore SDK for serialization
     */
    @Ignore
    constructor() : this(0L, "", "")

    @Ignore
    constructor(uid: String, phone: String) : this(0L, uid, phone)

}
package io.codelabs.churchinc.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "users")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = false)
    override val key: String,
    override var name: String,
    override var avatar: String?
) : SearchableDataModel {

    @Ignore
    constructor() : this("", "", null)
}
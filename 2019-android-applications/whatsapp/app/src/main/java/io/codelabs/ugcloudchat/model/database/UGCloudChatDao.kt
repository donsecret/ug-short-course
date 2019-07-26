package io.codelabs.ugcloudchat.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import io.codelabs.ugcloudchat.model.WhatsappUser

@Dao
interface UGCloudChatDao {

    /**
     * Get a single list of all the users
     */
    @Query("SELECT * FROM users ORDER BY timestamp DESC")
    fun getAllUsers(): MutableList<WhatsappUser>


    /**
     * Get all user asynchronously
     */
    @Query("SELECT * FROM users ORDER BY timestamp DESC")
    fun getAllUsersAsync(): LiveData<MutableList<WhatsappUser>>

    /**
     * Get a user by [uid]
     */
    @Query("SELECT * FROM users WHERE uid = :uid")
    fun getUserByUID(uid: String): LiveData<WhatsappUser>

    /**
     * Get a user by [id]
     */
    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Long): LiveData<WhatsappUser>

    /**
     * Add a list of users
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(users: MutableList<WhatsappUser>)

    /**
     * Remove users from the database
     */
    @Delete
    fun removeUser(vararg users: WhatsappUser)

    /**
     * Update a list of users
     */
    @Update
    fun updateUsers(vararg users: MutableList<WhatsappUser>)

    /**
     * Update a user
     */
    @Update
    fun updateUser(user: WhatsappUser)

    /**
     * Gets the currently logged in user
     */
    @Query("SELECT * FROM users WHERE uid = :uid LIMIT 1")
    fun getCurrentUser(uid: String): LiveData<WhatsappUser>
}
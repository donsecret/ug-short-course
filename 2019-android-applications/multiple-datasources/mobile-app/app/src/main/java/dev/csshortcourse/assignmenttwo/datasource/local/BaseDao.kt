package dev.csshortcourse.assignmenttwo.datasource.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

/**
 * Base Data Access Object
 */
interface BaseDao<O> {
    @Insert
    fun insert(item: O)

    @Update
    fun update(item: O)

    @Insert
    fun insertAll(item: MutableList<O>)

    @Delete
    fun remove(item: O)
}
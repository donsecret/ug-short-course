package dev.csshortcourse.assignmenttwo.datasource.local

/**
 * Base Data Access Object
 */
interface BaseDao<O> {
    //    @Insert
    fun insert(item: O)

    //    @Update
    fun update(item: O)

    //    @Insert
    fun insertAll(item: MutableList<O>)

    //    @Delete
    fun remove(item: O)
}
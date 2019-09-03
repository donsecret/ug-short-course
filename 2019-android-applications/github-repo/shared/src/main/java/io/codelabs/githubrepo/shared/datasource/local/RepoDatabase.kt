/*
 * Copyright (c) 2019. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package io.codelabs.githubrepo.shared.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.codelabs.githubrepo.shared.util.Constants

@Database(entities = [], version = Constants.DB_VER, exportSchema = true)
abstract class RepoDatabase : RoomDatabase() {
    abstract fun repoDao()

    companion object {
        @Volatile
        private var instance: RepoDatabase? = null

        // Main entry point for this database class
        fun get(context: Context): RepoDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(context, RepoDatabase::class.java, Constants.APP_DB)
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // todo: perform tasks when database is created for the first time
                    }
                })
                .build().also { instance = it }
        }
    }
}
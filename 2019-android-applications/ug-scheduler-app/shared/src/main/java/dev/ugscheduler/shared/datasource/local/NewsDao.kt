package dev.ugscheduler.shared.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import dev.ugscheduler.shared.data.News

@Dao
interface NewsDao : BaseDao<News> {

    @Query("select * from news order by timestamp desc")
    fun getNewsArticles(): LiveData<MutableList<News>>

    @Query("select * from news order by timestamp desc")
    fun getNewsArticlesAsync(): MutableList<News>
}
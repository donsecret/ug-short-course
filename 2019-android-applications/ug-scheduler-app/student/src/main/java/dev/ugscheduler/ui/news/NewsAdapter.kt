package dev.ugscheduler.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.ugscheduler.R
import dev.ugscheduler.shared.data.News

class NewsViewHolder(val v: View) : RecyclerView.ViewHolder(v)

private val NEWS_DIFF: DiffUtil.ItemCallback<News> = object : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean = oldItem == newItem
}

class NewsAdapter : ListAdapter<News, NewsViewHolder>(NEWS_DIFF) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_news_article,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
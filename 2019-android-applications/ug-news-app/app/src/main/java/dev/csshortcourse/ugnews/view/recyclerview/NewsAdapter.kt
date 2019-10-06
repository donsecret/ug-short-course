package dev.csshortcourse.ugnews.view.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import dev.csshortcourse.ugnews.databinding.ItemNewsBinding
import dev.csshortcourse.ugnews.model.NewsArticle
import dev.csshortcourse.ugnews.util.browse
import dev.csshortcourse.ugnews.util.debugger
import dev.csshortcourse.ugnews.util.layoutInflater

class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(news: NewsArticle) {
        binding.newsAuthor.text = news.author
        binding.newsDesc.text = news.desc
        binding.newsImage.load(news.image)
        binding.newsTitle.text = news.title
        debugger("News Image: ${news.image}")

        binding.root.setOnClickListener {
            binding.root.context.browse(news.url)
        }
    }
}

class NewsAdapter : ListAdapter<NewsArticle, NewsViewHolder>(NEWS_DIFF) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(ItemNewsBinding.inflate(parent.context.layoutInflater))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    companion object {
        private val NEWS_DIFF: DiffUtil.ItemCallback<NewsArticle> =
            object : DiffUtil.ItemCallback<NewsArticle>() {
                override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean =
                    oldItem.url == newItem.url

                override fun areContentsTheSame(
                    oldItem: NewsArticle,
                    newItem: NewsArticle
                ): Boolean = oldItem == newItem
            }
    }
}
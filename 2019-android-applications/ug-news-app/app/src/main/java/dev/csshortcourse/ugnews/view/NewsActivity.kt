package dev.csshortcourse.ugnews.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dev.csshortcourse.ugnews.R
import dev.csshortcourse.ugnews.databinding.ActivityNewsBinding
import dev.csshortcourse.ugnews.util.debugger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swipeRefresh.setOnRefreshListener {
            // todo: perform search to data source
        }

        binding.newsList.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(false)
        }

        binding.sourceGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.cnbc -> {
                    fetchData("cnbc")
                }

                R.id.abc_news -> {
                    fetchData("abc-news")
                }

                R.id.techcrunch -> {
                    fetchData("techcrunch")
                }
            }
        }

        // Fetch news from source
        fetchData("techcrunch")
    }

    private fun fetchData(source: String) {
        debugger("News source is $source")
        binding.swipeRefresh.isRefreshing = true
        CoroutineScope(Main).launch {
            delay(2500)
            binding.swipeRefresh.isRefreshing = false
            Snackbar.make(binding.container, "Refresh completed", Snackbar.LENGTH_SHORT).show()
        }
    }
}

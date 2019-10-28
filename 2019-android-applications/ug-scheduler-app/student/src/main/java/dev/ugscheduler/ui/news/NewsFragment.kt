package dev.ugscheduler.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import dev.ugscheduler.databinding.NewsFragmentBinding
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.debugger
import dev.ugscheduler.shared.util.setupWithAdapter
import dev.ugscheduler.shared.viewmodel.AppViewModel
import dev.ugscheduler.shared.viewmodel.AppViewModelFactory
import dev.ugscheduler.util.MainNavigationFragment
import org.koin.android.ext.android.get

class NewsFragment : MainNavigationFragment() {
    private lateinit var binding: NewsFragmentBinding
    private val viewModel by lazy {
        activityViewModelProvider<NewsViewModel>(
            NewsViewModelFactory(
                get()
            )
        )
    }
    private val appViewModel by lazy {
        activityViewModelProvider<AppViewModel>(
            AppViewModelFactory(
                get()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val newsAdapter = NewsAdapter()

        // Get news from source
        viewModel.news.observe(viewLifecycleOwner, Observer { news ->
            binding.recyclerView.setupWithAdapter(newsAdapter)
            // todo: Display News in a recyclerview
            debugger(news)
        })


    }

}

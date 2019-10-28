package dev.ugscheduler.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.ugscheduler.R
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.debugger
import dev.ugscheduler.shared.viewmodel.AppViewModel
import dev.ugscheduler.shared.viewmodel.AppViewModelFactory
import dev.ugscheduler.util.MainNavigationFragment
import org.koin.android.ext.android.get

class NewsFragment : MainNavigationFragment() {
    //    private lateinit var binding: FragmentNews
    private val viewModel by lazy { activityViewModelProvider<NewsViewModel>(NewsViewModelFactory(get())) }
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
        return inflater.inflate(R.layout.news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Get news from source
        viewModel.news.observe(viewLifecycleOwner, Observer { news ->
            // todo: Display News in a recyclerview
            debugger(news)
        })


    }

}

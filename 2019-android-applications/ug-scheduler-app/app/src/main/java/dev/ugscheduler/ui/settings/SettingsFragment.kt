package dev.ugscheduler.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dev.ugscheduler.databinding.SettingsFragmentBinding
import dev.ugscheduler.util.MainNavigationFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingsFragment : MainNavigationFragment() {
    private lateinit var binding: SettingsFragmentBinding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        binding.swipeRefresh.setOnRefreshListener {
            // todo: remove this simulated loading action
            uiScope.launch {
                delay(3000)
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }


}

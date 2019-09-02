package dev.ugscheduler.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import dev.ugscheduler.databinding.SettingsFragmentBinding
import dev.ugscheduler.shared.datasource.local.StudentDao
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.prefs.UserSharedPreferences
import dev.ugscheduler.util.MainNavigationFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

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
        viewModel = activityViewModelProvider(SettingsViewModelFactory())

        binding.swipeRefresh.setOnRefreshListener {
            // todo: remove this simulated loading action
            uiScope.launch {
                delay(3000)
                binding.swipeRefresh.isRefreshing = false
            }
        }

        // Get current user's information
        val dao: StudentDao = get()
        val prefs: UserSharedPreferences = get()
        dao.getStudent(prefs.uid).observe(viewLifecycleOwner, Observer { student ->
            binding.student = student
            binding.viewModel = viewModel
        })
    }


}

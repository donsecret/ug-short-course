package dev.ugscheduler.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import dev.ugscheduler.databinding.SettingsFragmentBinding
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.debugger
import dev.ugscheduler.shared.viewmodel.AppViewModel
import dev.ugscheduler.shared.viewmodel.AppViewModelFactory
import dev.ugscheduler.util.MainNavigationFragment
import org.koin.android.ext.android.get

class SettingsFragment : MainNavigationFragment() {

    private val viewModel by lazy { activityViewModelProvider<AppViewModel>(AppViewModelFactory(get())) }
    private lateinit var binding: SettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getCurrentStudent(false).observe(viewLifecycleOwner, Observer { student ->
            debugger(student)
        })


    }

}

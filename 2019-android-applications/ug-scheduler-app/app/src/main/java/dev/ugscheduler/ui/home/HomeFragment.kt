package dev.ugscheduler.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePaddingRelative
import androidx.lifecycle.ViewModelProvider
import dev.ugscheduler.databinding.FragmentHomeBinding
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.doOnApplyWindowInsets
import dev.ugscheduler.ui.auth.AuthViewModelFactory
import dev.ugscheduler.util.MainNavigationFragment
import dev.ugscheduler.util.setupProfileMenuItem

class HomeFragment : MainNavigationFragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Pad the bottom of the RecyclerView so that the content scrolls up above the nav bar
        binding.recyclerView.doOnApplyWindowInsets { v, insets, padding ->
            v.updatePaddingRelative(bottom = padding.bottom + insets.systemWindowInsetBottom)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.toolbar.setupProfileMenuItem(
            activityViewModelProvider(AuthViewModelFactory()),
            viewLifecycleOwner
        )
    }

}

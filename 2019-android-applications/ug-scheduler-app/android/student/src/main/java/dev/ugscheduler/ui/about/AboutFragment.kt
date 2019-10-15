package dev.ugscheduler.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePaddingRelative
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import dev.ugscheduler.BuildConfig
import dev.ugscheduler.databinding.AboutFragmentBinding
import dev.ugscheduler.shared.util.doOnApplyWindowInsets

class AboutFragment : Fragment() {

    private lateinit var binding: AboutFragmentBinding
    private lateinit var viewModel: AboutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AboutFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AboutViewModel::class.java)

        binding.appVersion.text = String.format("v%s", BuildConfig.VERSION_NAME)

        val adapter = AboutLibsAdapter()
        val libs = LibraryDeserializer.deserialize(requireContext())
        with(binding.libsList) {
            this.adapter = adapter
            this.setHasFixedSize(false)
            this.itemAnimator = DefaultItemAnimator()
        }
        adapter.submitList(libs)

        // Padding at the bottom of the list
        binding.libsList.doOnApplyWindowInsets { v, insets, padding ->
            v.updatePaddingRelative(bottom = padding.bottom + insets.systemWindowInsetBottom)
        }
    }

}

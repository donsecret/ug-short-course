package dev.ugscheduler.ui.about


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePaddingRelative
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import dev.ugscheduler.databinding.FragmentAboutBinding
import dev.ugscheduler.shared.util.debugger
import dev.ugscheduler.shared.util.doOnApplyWindowInsets
import dev.ugscheduler.util.MainNavigationFragment
import java.util.*


class AboutFragment : MainNavigationFragment() {

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = AboutLibsAdapter()
        val libs = LibraryDeserializer.deserialize(requireContext())
        adapter.submitList(libs)
        with(binding.libsList) {
            this.adapter = adapter
            this.setHasFixedSize(false)
            this.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        }

        // Padding at the bottom of the list
        binding.libsList.doOnApplyWindowInsets { v, insets, padding ->
            v.updatePaddingRelative(bottom = padding.bottom + insets.systemWindowInsetBottom)
        }
    }


}

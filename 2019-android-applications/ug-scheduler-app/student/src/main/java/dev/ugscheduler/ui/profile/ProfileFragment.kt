/*
 * Copyright (c) 2019. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import dev.ugscheduler.R
import dev.ugscheduler.shared.data.Facilitator
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.debugger
import dev.ugscheduler.shared.viewmodel.AppViewModel
import dev.ugscheduler.shared.viewmodel.AppViewModelFactory
import dev.ugscheduler.util.MainNavigationFragment
import org.koin.android.ext.android.get

class ProfileFragment : DialogFragment() {
    //private lateinit var binding: Fragment
    private val viewModel: AppViewModel by lazy {
        activityViewModelProvider<AppViewModel>(
            AppViewModelFactory(get())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val facilitator = arguments?.get("extra_facilitator") as? Facilitator
        if (facilitator != null) bindUI(facilitator) else findNavController().popBackStack()
    }

    private fun bindUI(facilitator: Facilitator) {
        // todo: show facilitator's profile information
        debugger("Current facilitator => $facilitator")
    }

    override fun onResume() {
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        super.onResume()
    }

}

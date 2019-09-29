/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import dev.ugscheduler.databinding.SignOutFragmentBinding
import dev.ugscheduler.shared.datasource.local.StudentDao
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.prefs.UserSharedPreferences
import dev.ugscheduler.shared.viewmodel.AppViewModel
import dev.ugscheduler.shared.viewmodel.AppViewModelFactory
import org.koin.android.ext.android.get

class SignOutFragment : DialogFragment() {
    private lateinit var binding: SignOutFragmentBinding
    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignOutFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activityViewModelProvider(AppViewModelFactory(get()))

        binding.viewModel = viewModel
        val dao: StudentDao = get()
        val prefs: UserSharedPreferences = get()
        dao.getStudent(prefs.uid).observe(viewLifecycleOwner, Observer { student ->
            binding.apply {
                this.student = student
                this.signOut.setOnClickListener {
                    viewModel?.logout()
                    dismiss()
                }
            }
        })
    }

    override fun onResume() {
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        super.onResume()
    }

}

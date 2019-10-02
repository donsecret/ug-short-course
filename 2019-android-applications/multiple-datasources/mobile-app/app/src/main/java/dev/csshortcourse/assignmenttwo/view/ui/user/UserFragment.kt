package dev.csshortcourse.assignmenttwo.view.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dev.csshortcourse.assignmenttwo.databinding.FragmentUserBinding
import dev.csshortcourse.assignmenttwo.util.BaseFragment


class UserFragment : BaseFragment() {

    private lateinit var binding: FragmentUserBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Setup navigation with the toolbar
        //binding.toolbar.setupWithNavController(findNavController())
    }

}

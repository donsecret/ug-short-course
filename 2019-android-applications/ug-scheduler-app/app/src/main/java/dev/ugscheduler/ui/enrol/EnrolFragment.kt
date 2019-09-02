package dev.ugscheduler.ui.enrol


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.ugscheduler.databinding.FragmentEnrolBinding
import dev.ugscheduler.util.MainNavigationFragment


class EnrolFragment : MainNavigationFragment() {
    private lateinit var binding: FragmentEnrolBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnrolBinding.inflate(inflater, container, false)
        return binding.root
    }


}

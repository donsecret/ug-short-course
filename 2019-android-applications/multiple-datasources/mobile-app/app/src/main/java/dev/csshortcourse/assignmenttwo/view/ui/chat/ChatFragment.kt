package dev.csshortcourse.assignmenttwo.view.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dev.csshortcourse.assignmenttwo.databinding.FragmentHomeBinding
import dev.csshortcourse.assignmenttwo.util.*
import dev.csshortcourse.assignmenttwo.view.adapter.UserAdapter

class ChatFragment : BaseFragment() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentHomeBinding.inflate(inflater)
        viewModel =
            ViewModelProvider(this).get(ChatViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = UserAdapter(requireActivity() as BaseActivity)
        binding.chatsList.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(requireContext())
            this.setHasFixedSize(false)
            this.itemAnimator = DefaultItemAnimator()
        }

        // Fetch data from view model
        viewModel.users.observe(viewLifecycleOwner, Observer { users ->
            binding.loading.gone()
            debugger("Users returned: $users")
            if (users.isEmpty()) {
                binding.emptyList.visible()
            } else
                adapter.submitList(users)
        })
    }
}

package dev.csshortcourse.assignmenttwo.view.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dev.csshortcourse.assignmenttwo.R
import dev.csshortcourse.assignmenttwo.databinding.FragmentHomeBinding
import dev.csshortcourse.assignmenttwo.model.User
import dev.csshortcourse.assignmenttwo.util.*
import dev.csshortcourse.assignmenttwo.view.ConversationActivity
import dev.csshortcourse.assignmenttwo.view.adapter.UserAdapter
import dev.csshortcourse.assignmenttwo.view.adapter.UserClickListener

/**
 * Chat fragment
 * Shows user's current chats
 */
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

        // Setup adapter for the recyclerview
        val adapter = UserAdapter(requireActivity() as BaseActivity, object : UserClickListener {
            override fun onClick(user: User) {
                // Pass data to the conversation fragment
                (requireActivity() as BaseActivity).moveTo(
                    ConversationActivity::class.java,
                    false,
                    bundleOf(Pair(ConversationActivity.USER, user))
                )
            }
        })

        // setup recyclerview
        binding.chatsList.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(requireContext())
            this.setHasFixedSize(false)
            this.itemAnimator = DefaultItemAnimator()
        }

        binding.addUser.setOnClickListener {
            // Navigate to the get all users fragment
            findNavController().navigate(R.id.navigation_users)
        }

        // Fetch data from view model
        viewModel.users.observe(viewLifecycleOwner, Observer { users ->
            binding.loading.gone()
            if (users.isEmpty()) {
                binding.emptyList.visible()
            } else
                adapter.submitList(users)
        })
    }
}

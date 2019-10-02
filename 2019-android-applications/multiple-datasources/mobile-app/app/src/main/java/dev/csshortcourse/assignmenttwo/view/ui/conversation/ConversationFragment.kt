package dev.csshortcourse.assignmenttwo.view.ui.conversation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dev.csshortcourse.assignmenttwo.databinding.ConversationFragmentBinding
import dev.csshortcourse.assignmenttwo.model.User
import dev.csshortcourse.assignmenttwo.util.BaseFragment
import dev.csshortcourse.assignmenttwo.view.ui.chat.ChatFragmentDirections

class ConversationFragment : BaseFragment() {
    // Get arguments from bundle sent across fragments
    //private val args: Convers by navArgs()

    private lateinit var binding: ConversationFragmentBinding
    private lateinit var viewModel: ConversationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ConversationFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(ConversationViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Get bundle from args
//        binding.bundleData.text = arguments?.getParcelable<User>("extra_user")?.name
    }

}

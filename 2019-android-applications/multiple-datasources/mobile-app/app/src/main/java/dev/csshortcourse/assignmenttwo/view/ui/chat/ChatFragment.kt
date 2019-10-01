package dev.csshortcourse.assignmenttwo.view.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.csshortcourse.assignmenttwo.R
import dev.csshortcourse.assignmenttwo.util.BaseFragment
import dev.csshortcourse.assignmenttwo.util.debugger

class ChatFragment : BaseFragment() {

    private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(ChatViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.users.observe(viewLifecycleOwner, Observer { users ->
            debugger(users)

            // todo: add to adapter
        })
    }

}

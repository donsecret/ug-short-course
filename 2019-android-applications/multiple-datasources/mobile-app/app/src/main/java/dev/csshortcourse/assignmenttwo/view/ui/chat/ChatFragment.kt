package dev.csshortcourse.assignmenttwo.view.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dev.csshortcourse.assignmenttwo.R

class ChatFragment : Fragment() {

    private lateinit var homeViewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(ChatViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}

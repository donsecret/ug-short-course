package dev.csshortcourse.assignmenttwo.view.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.csshortcourse.assignmenttwo.R
import dev.csshortcourse.assignmenttwo.util.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatFragment : BaseFragment() {

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*uiScope.launch {
            delay(850)
            MaterialAlertDialogBuilder(requireContext()).apply {
                setTitle(getString(R.string.app_name))
                setMessage("We are happy to have you on board. Start a new conversation.")
                setPositiveButton("Dismiss") { d, _ -> d.dismiss() }
                show()
            }
        }*/
    }

}

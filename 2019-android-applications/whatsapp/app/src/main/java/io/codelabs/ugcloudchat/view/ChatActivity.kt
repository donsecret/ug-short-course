package io.codelabs.ugcloudchat.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import io.codelabs.ugcloudchat.R
import io.codelabs.ugcloudchat.model.Chat
import io.codelabs.ugcloudchat.model.WhatsappUser
import io.codelabs.ugcloudchat.util.debugThis
import io.codelabs.ugcloudchat.util.toast
import io.codelabs.ugcloudchat.viewmodel.ChatViewModel
import kotlinx.android.synthetic.main.activity_chat.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatActivity : BaseActivity() {

    private val viewModel by viewModel<ChatViewModel>()

    // Store recipient's details
    private var user: WhatsappUser? = null
    private var id: Long? = null


    // Store current user's uid
    private val currentUser: String? by lazy { prefs.uid }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        if (intent.hasExtra(ID)) {
            debugThis(intent.getLongExtra(ID, -1L))
            debugThis(intent.getParcelableExtra(USER))

            id = intent.getLongExtra(ID, -1L)
            user = intent.getParcelableExtra<WhatsappUser>(USER)

            debugThis("Showing chats with: $user")

            if (id != null && id != -1L) {
                viewModel.getMyChatsWith(id.toString()).observe(this, Observer { allChats ->
                    //                    if(allChats != null) adapter.addChats(allChats)
                    debugThis("Loading all chats as: $allChats")
                })
            }
        } else {
            toast("You cannot start a conversation with a user that does not exist")
            finish()
        }


    }

    /**
     * Send message
     */
    fun sendMessage(view: View?) {
        // Create message
        val chat = Chat(
            "",
            message_input.text.toString(),
            currentUser!!,
            user?.phone ?: user?.id.toString()
        )
        viewModel.sendMessage(chat)
    }

    companion object {
        const val ID = "id"
        const val USER = "user"
    }
}

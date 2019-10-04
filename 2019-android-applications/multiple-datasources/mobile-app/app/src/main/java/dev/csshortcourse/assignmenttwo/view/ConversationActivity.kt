package dev.csshortcourse.assignmenttwo.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.csshortcourse.assignmenttwo.R
import dev.csshortcourse.assignmenttwo.databinding.ActivityConversationBinding
import dev.csshortcourse.assignmenttwo.model.Chat
import dev.csshortcourse.assignmenttwo.model.User
import dev.csshortcourse.assignmenttwo.util.BaseActivity
import dev.csshortcourse.assignmenttwo.view.adapter.ChatClickListener
import dev.csshortcourse.assignmenttwo.view.adapter.ConversationAdapter
import java.util.*

class ConversationActivity : BaseActivity() {
    private lateinit var binding: ActivityConversationBinding
    private var user: User? = null
    private lateinit var adapter: ConversationAdapter
    private val currentUser: User by lazy { User(UUID.randomUUID().toString(), "Quabynah Bilson") }
    private val oldList by lazy { mutableListOf<Chat>() }
    private val viewModel: ConversationViewModel by viewModels<ConversationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get user from the intent data
        user = intent.getParcelableExtra<User>(USER)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.toolbar.title = user?.name ?: getString(R.string.title_conversation)

        adapter = ConversationAdapter(
            this,
            currentUser,
            object : ChatClickListener {
                override fun onClick(chat: Chat) {
                    viewModel.getUser(chat.sender).observe(
                        this@ConversationActivity,
                        androidx.lifecycle.Observer { chatUser ->
                            MaterialAlertDialogBuilder(this@ConversationActivity).apply {
                                setTitle("Conversation details")
                                setMessage("${chat.message}\n\nSent by: ${chatUser.name}")
                                show()
                            }
                        })
                }
            })
        binding.conversationList.apply {
            this.adapter = this@ConversationActivity.adapter
            this.layoutManager = LinearLayoutManager(
                this@ConversationActivity,
                LinearLayoutManager.VERTICAL,
                false
            ).apply {
                stackFromEnd = true
            }
            this.itemAnimator = DefaultItemAnimator()
        }

        // Observe conversation
        viewModel.getConversation().observe(this, androidx.lifecycle.Observer { messages ->
            adapter.submitList(messages) {
                // Scroll to last item in the list if it is not empty
                if (adapter.itemCount != 0) binding.conversationList.smoothScrollToPosition(
                    adapter.itemCount.minus(
                        1
                    )
                )
            }
        })
    }

    companion object {
        const val USER = "extra_user"
    }
}

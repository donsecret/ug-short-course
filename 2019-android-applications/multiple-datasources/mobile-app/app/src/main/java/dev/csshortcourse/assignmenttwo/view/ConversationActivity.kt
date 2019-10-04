package dev.csshortcourse.assignmenttwo.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
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

class ConversationActivity : BaseActivity() {
    private lateinit var binding: ActivityConversationBinding
    private var user: User? = null
    private lateinit var adapter: ConversationAdapter
    private lateinit var currentUser: User
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

        // Get current user instance
        viewModel.getCurrentUser().observe(this, androidx.lifecycle.Observer {
            currentUser = it

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
            if (user != null) {
                viewModel.getConversation(user!!.id)
                    .observe(this, androidx.lifecycle.Observer { messages ->
                        adapter.submitList(messages) {
                            // Scroll to last item in the list if it is not empty
                            if (adapter.itemCount != 0) binding.conversationList.smoothScrollToPosition(
                                adapter.itemCount.minus(
                                    1
                                )
                            )
                        }
                    })
            } else onBackPressed()
        })
    }

    fun sendMessage(v: View) {
        if (user != null){
            val message = binding.message.text.toString()
            if (message.isEmpty()){
                Toast.makeText(applicationContext, "Please enter a message first", Toast.LENGTH_SHORT).show()
            } else {
                binding.message.text?.clear()
                viewModel.addMessage(currentUser.id, user!!.id, message)
            }
        }
    }

    companion object {
        const val USER = "extra_user"
    }
}

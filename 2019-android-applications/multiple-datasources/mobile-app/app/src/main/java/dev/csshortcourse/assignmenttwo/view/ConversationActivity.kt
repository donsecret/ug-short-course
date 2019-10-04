package dev.csshortcourse.assignmenttwo.view

import android.os.Bundle
import dev.csshortcourse.assignmenttwo.R
import dev.csshortcourse.assignmenttwo.databinding.ActivityConversationBinding
import dev.csshortcourse.assignmenttwo.model.User
import dev.csshortcourse.assignmenttwo.util.BaseActivity
import dev.csshortcourse.assignmenttwo.util.debugger

class ConversationActivity : BaseActivity() {
    private lateinit var binding: ActivityConversationBinding
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get user from the intent data
        user = intent.getParcelableExtra<User>(USER)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.toolbar.title = user?.name ?: getString(R.string.title_conversation)

    }

    companion object {
        const val USER = "extra_user"
    }
}

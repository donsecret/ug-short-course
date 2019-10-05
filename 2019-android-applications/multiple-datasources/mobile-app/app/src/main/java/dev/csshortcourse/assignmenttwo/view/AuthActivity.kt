package dev.csshortcourse.assignmenttwo.view

import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import dev.csshortcourse.assignmenttwo.databinding.ActivityAuthBinding
import dev.csshortcourse.assignmenttwo.model.User
import dev.csshortcourse.assignmenttwo.util.BaseActivity
import dev.csshortcourse.assignmenttwo.util.moveTo
import dev.csshortcourse.assignmenttwo.view.ui.user.UserViewModel

class AuthActivity : BaseActivity() {
    private lateinit var binding: ActivityAuthBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe login state
        prefs.liveLoginState.observe(this, Observer {
            if (it)
                moveTo(HomeActivity::class.java, true)
        })

        // Toggle button state
        binding.authName.addTextChangedListener { text ->
            binding.login.isEnabled =
                text.toString().isNotEmpty() && !TextUtils.isDigitsOnly(text.toString())
        }


        // Save logged in user
        binding.login.setOnClickListener {
            val user = User(binding.authName.text.toString())
            prefs.login(user.id)

        }
    }
}

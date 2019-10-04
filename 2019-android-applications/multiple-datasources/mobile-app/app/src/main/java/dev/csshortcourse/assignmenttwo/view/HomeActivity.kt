package dev.csshortcourse.assignmenttwo.view

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dev.csshortcourse.assignmenttwo.R
import dev.csshortcourse.assignmenttwo.databinding.ActivityHomeBinding
import dev.csshortcourse.assignmenttwo.util.BaseActivity
import dev.csshortcourse.assignmenttwo.util.debugger
import java.util.*

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup NavController
        val navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)

        debugger(UUID.randomUUID())
        debugger(System.currentTimeMillis())
    }
}

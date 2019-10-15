package dev.ugscheduler.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dev.ugscheduler.R
import dev.ugscheduler.databinding.ActivityHomeBinding
import dev.ugscheduler.databinding.NavigationHeaderBinding
import dev.ugscheduler.shared.util.BaseActivity
import dev.ugscheduler.shared.util.HeightTopWindowInsetsListener
import dev.ugscheduler.shared.util.NoopWindowInsetsListener
import dev.ugscheduler.shared.util.prefs.UserSharedPreferences
import dev.ugscheduler.shared.util.shouldCloseDrawerFromBackPress
import dev.ugscheduler.shared.widgets.NavigationBarContentFrameLayout
import dev.ugscheduler.util.MainNavigationFragment
import dev.ugscheduler.util.NavigationHost
import org.koin.android.ext.android.inject

class HomeActivity : BaseActivity(), NavigationHost {
    private lateinit var binding: ActivityHomeBinding

    private val prefs by inject<UserSharedPreferences>()

    private lateinit var navHeaderBinding: NavigationHeaderBinding
    private lateinit var navController: NavController
    private var navHostFragment: NavHostFragment? = null
    private var currentNavId = NAV_ID_NONE

    companion object {
        /** Key for an int extra defining the initial navigation target. */
        const val EXTRA_NAVIGATION_ID = "extra.NAVIGATION_ID"

        private const val NAV_ID_NONE = -1

        private val TOP_LEVEL_DESTINATIONS = setOf(
            R.id.navigation_home,
            R.id.navigation_my_courses,
            R.id.navigation_map,
            R.id.navigation_info,
            R.id.navigation_sessions,
            R.id.navigation_about,
            R.id.navigation_chat,
            R.id.navigation_settings
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val drawerContainer: NavigationBarContentFrameLayout = findViewById(R.id.drawer_container)
        // Let's consume any
        drawerContainer.setOnApplyWindowInsetsListener { v, insets ->
            // Let the view draw it's navigation bar divider
            v.onApplyWindowInsets(insets)

            // Consume any horizontal insets and pad all content in. There's not much we can do
            // with horizontal insets
            v.updatePadding(
                left = insets.systemWindowInsetLeft,
                right = insets.systemWindowInsetRight
            )
            insets.replaceSystemWindowInsets(
                0, insets.systemWindowInsetTop,
                0, insets.systemWindowInsetBottom
            )
        }


        binding.content.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        // Make the content ViewGroup ignore insets so that it does not use the default padding
        binding.content.setOnApplyWindowInsetsListener(NoopWindowInsetsListener)

        binding.statusBarScrim.setOnApplyWindowInsetsListener(HeightTopWindowInsetsListener)

        navHeaderBinding = NavigationHeaderBinding.inflate(layoutInflater).apply {
            //lifecycleOwner = this@HomeActivity
        }

        if (savedInstanceState == null) {
            // default to showing Home
            val initialNavId = intent.getIntExtra(EXTRA_NAVIGATION_ID, R.id.navigation_home)
            binding.navigation.setCheckedItem(initialNavId) // doesn't trigger listener
            navigateTo(initialNavId)
        }
    }


    override fun registerToolbarWithNavigation(toolbar: Toolbar) {
        val appBarConfiguration = AppBarConfiguration(TOP_LEVEL_DESTINATIONS, binding.drawer)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentNavId = binding.navigation.checkedItem?.itemId ?: NAV_ID_NONE
    }

    override fun onBackPressed() {
        /**
         * If the drawer is open, the behavior changes based on the API level.
         * When gesture nav is enabled (Q+), we want back to exit when the drawer is open.
         * When button navigation is enabled (on Q or pre-Q) we want to close the drawer on back.
         */
        if (binding.drawer.isDrawerOpen(binding.navigation) && binding.drawer.shouldCloseDrawerFromBackPress()) {
            closeDrawer()
        } else {
            super.onBackPressed()
        }
    }

    private fun closeDrawer() {
        binding.drawer.closeDrawer(GravityCompat.START)
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        getCurrentFragment()?.onUserInteraction()
    }

    private fun getCurrentFragment(): MainNavigationFragment? {
        return navHostFragment
            ?.childFragmentManager
            ?.primaryNavigationFragment as? MainNavigationFragment
    }

    private fun navigateTo(navId: Int) {
        if (navId == currentNavId) {
            return // user tapped the current item
        }
        navController.navigate(navId)
    }
}

package dev.ugscheduler.ui.home

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.updatePadding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import dev.ugscheduler.R
import dev.ugscheduler.databinding.NavigationHeaderBinding
import dev.ugscheduler.shared.util.*
import dev.ugscheduler.shared.util.prefs.UserSharedPreferences
import dev.ugscheduler.shared.widgets.NavigationBarContentFrameLayout
import dev.ugscheduler.util.MainNavigationFragment
import dev.ugscheduler.util.NavigationHost
import org.koin.android.ext.android.inject

class HomeActivity : BaseActivity(), NavigationHost {
    private val prefs by inject<UserSharedPreferences>()

    companion object {
        /** Key for an int extra defining the initial navigation target. */
        const val EXTRA_NAVIGATION_ID = "extra.NAVIGATION_ID"

        private const val NAV_ID_NONE = -1

        private const val DIALOG_SIGN_IN = "dialog_sign_in"
        private const val DIALOG_SIGN_OUT = "dialog_sign_out"

        private val TOP_LEVEL_DESTINATIONS = setOf(
            R.id.navigation_home,
            R.id.navigation_courses,
            R.id.navigation_my_courses,
            R.id.navigation_map,
            R.id.navigation_info,
            R.id.navigation_sessions,
            R.id.navigation_about,
            R.id.navigation_chat,
            R.id.navigation_settings
        )
    }

    private lateinit var content: FrameLayout
    private lateinit var drawer: DrawerLayout
    private lateinit var navigation: NavigationView
    private lateinit var navHeaderBinding: NavigationHeaderBinding
    private lateinit var navController: NavController
    private var navHostFragment: NavHostFragment? = null

    private lateinit var statusScrim: View

    private var currentNavId = NAV_ID_NONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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

        content = findViewById(R.id.content_container)
        content.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        // Make the content ViewGroup ignore insets so that it does not use the default padding
        content.setOnApplyWindowInsetsListener(NoopWindowInsetsListener)

        statusScrim = findViewById(R.id.status_bar_scrim)
        statusScrim.setOnApplyWindowInsetsListener(HeightTopWindowInsetsListener)

        drawer = findViewById(R.id.drawer)

        navHeaderBinding = NavigationHeaderBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@HomeActivity
        }

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

        navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentNavId = destination.id
            val isTopLevelDestination = TOP_LEVEL_DESTINATIONS.contains(destination.id)
            val lockMode = if (isTopLevelDestination) {
                DrawerLayout.LOCK_MODE_UNLOCKED
            } else {
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED
            }
            drawer.setDrawerLockMode(lockMode)
        }

        navigation = findViewById(R.id.navigation)
        navigation.apply {
            // Update the Navigation header view to pad itself down
            navHeaderBinding.root.doOnApplyWindowInsets { v, insets, padding ->
                v.updatePadding(top = padding.top + insets.systemWindowInsetTop)
            }
            addHeaderView(navHeaderBinding.root)
            itemBackground = navigationItemBackground(context)

            prefs.liveLoginState.observe(this@HomeActivity, Observer { state ->
                menu.findItem(R.id.navigation_chat).isVisible = state
                menu.findItem(R.id.navigation_my_courses).isVisible = state
                menu.findItem(R.id.navigation_settings).isVisible = state
                menu.findItem(R.id.navigation_sessions).isVisible = state
            })

            setupWithNavController(navController)
        }

        if (savedInstanceState == null) {
            // default to showing Home
            val initialNavId = intent.getIntExtra(EXTRA_NAVIGATION_ID, R.id.navigation_home)
            navigation.setCheckedItem(initialNavId) // doesn't trigger listener
            navigateTo(initialNavId)
        }
    }

    override fun registerToolbarWithNavigation(toolbar: Toolbar) {
        val appBarConfiguration = AppBarConfiguration(TOP_LEVEL_DESTINATIONS, drawer)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentNavId = navigation.checkedItem?.itemId ?: NAV_ID_NONE
    }

    override fun onBackPressed() {
        /**
         * If the drawer is open, the behavior changes based on the API level.
         * When gesture nav is enabled (Q+), we want back to exit when the drawer is open.
         * When button navigation is enabled (on Q or pre-Q) we want to close the drawer on back.
         */
        if (drawer.isDrawerOpen(navigation) && drawer.shouldCloseDrawerFromBackPress()) {
            closeDrawer()
        } else {
            super.onBackPressed()
        }
    }

    private fun closeDrawer() {
        drawer.closeDrawer(GravityCompat.START)
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
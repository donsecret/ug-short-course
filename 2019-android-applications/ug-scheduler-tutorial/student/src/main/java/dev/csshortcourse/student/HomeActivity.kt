package dev.csshortcourse.student

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import dev.csshortcourse.shared.BaseActivity
import dev.csshortcourse.shared.debugger
import dev.csshortcourse.shared.toast
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {


    // todo: Items to do
    // 1. Configure the drawer layout to have the Hamburger Icon
    // 2. Allow back button to close drawer if it is opened
    // 3. Setup our fragments

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        // Configure the drawer layout
        val drawerListener = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawer.addDrawerListener(drawerListener)
        drawerListener.syncState()

        // Configure Navigation View to handle clicks for the menu items
        nav_view.setNavigationItemSelectedListener(this)

        // Default checked menu item
        nav_view.setCheckedItem(nav_view.menu.findItem(R.id.menu_home)).apply {
            addFragment(HomeFragment())
        }

        // Opens the drawer by default
        // drawer.openDrawer(GravityCompat.START)
    }

    private fun handleFragments(item: MenuItem) {
        when (item.itemId) {
            R.id.menu_about -> {
                addFragment(AboutFragment())
            }

            R.id.menu_home -> {
                addFragment(HomeFragment())
            }

            R.id.menu_map -> {
                addFragment(MapFragment())
            }
        }

        drawer.closeDrawers()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        handleFragments(item)
        return true
    }

    // Manages closing of drawer and the default back action for an activity
    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawers()
        else super.onBackPressed()
    }

    // We need the fragment manager in order to attach the fragment to the activity
    private fun addFragment(fragment: Fragment) {
        debugger("Adding fragment: ${fragment::class.java.simpleName}")

        // Actually add the fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_frame, fragment, fragment::class.java.simpleName)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_profile -> {
                // todo: open user's profile information
                toast("Not available")
            }

            R.id.menu_logout -> {
                // todo: sign out user
                toast("Not available")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

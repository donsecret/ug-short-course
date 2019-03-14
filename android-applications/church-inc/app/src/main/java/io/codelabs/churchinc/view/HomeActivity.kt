package io.codelabs.churchinc.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import io.codelabs.churchinc.R
import io.codelabs.churchinc.core.RootActivity
import io.codelabs.churchinc.core.datasource.remote.DataCallback
import io.codelabs.churchinc.core.datasource.remote.getLiveUser
import io.codelabs.churchinc.core.glide.GlideApp
import io.codelabs.churchinc.model.User
import io.codelabs.churchinc.util.toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.coroutines.launch

class HomeActivity : RootActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        val toggleListener = ActionBarDrawerToggle(
            this@HomeActivity,
            drawer_layout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawer_layout.addDrawerListener(toggleListener)
        nav_view.setNavigationItemSelectedListener(this)
        val headerView = nav_view.getHeaderView(0)
        setupHeader(headerView)
    }

    private fun setupHeader(headerView: View?) {
        if (headerView == null) return

        getLiveUser(object : DataCallback<User> {
            override fun onError(e: String?) {
                toast(e)
            }

            override fun onComplete(result: User?) {
                if (result == null) {
                    toast("User cannot be found")
                } else {

                    val displayName = auth.currentUser?.displayName
                    headerView.findViewById<TextView>(R.id.user_full_name).text =
                        if (displayName.isNullOrEmpty()) result.name else displayName
                    val avatar = headerView.findViewById<ImageView>(R.id.user_avatar)
                    headerView.findViewById<TextView>(R.id.user_email).text = auth.currentUser?.email
                    headerView.findViewById<TextView>(R.id.user_phone).text =
                        result.phone ?: auth.currentUser?.phoneNumber

                    GlideApp.with(this@HomeActivity)
                        .load(result.avatar)
                        .circleCrop()
                        .placeholder(R.drawable.avatar_header)
                        .error(R.drawable.avatar_header)
                        .into(avatar)
                }
            }
        })

        /*dao.getLiveUser(auth.uid!!).observe(this@HomeActivity, Observer { currentUser ->
            if (currentUser == null) {
                toast("User cannot be found")
            } else {

                val displayName = auth.currentUser?.displayName
                headerView.findViewById<TextView>(R.id.user_full_name).text =
                    if (displayName.isNullOrEmpty()) currentUser.name else displayName
                val avatar = headerView.findViewById<ImageView>(R.id.user_avatar)
                headerView.findViewById<TextView>(R.id.user_email).text = auth.currentUser?.email
                headerView.findViewById<TextView>(R.id.user_phone).text =
                    auth.currentUser?.phoneNumber ?: "No phone number"

                GlideApp.with(this@HomeActivity)
                    .load(currentUser.avatar)
                    .circleCrop()
                    .placeholder(R.drawable.avatar_header)
                    .error(R.drawable.avatar_header)
                    .into(avatar)
            }
        })*/
    }

    // Attach menu to toolbar from here
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_toolbar_menu, menu)
        return true
    }

    // Access individual menu items from here by ids
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_account -> {
                //todo: view user's profile information
            }

            R.id.menu_logout -> {
                ioScope.launch {
                    val currentUser = dao.getCurrentUser(auth.uid!!)
                    dao.deleteUser(currentUser)

                    uiScope.launch {
                        auth.signOut()
                        startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                        finishAfterTransition()
                    }
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.menu_background_info -> {
                toast(p0.title.toString())
            }
            R.id.menu_location -> {
                toast(p0.title.toString())
            }
            R.id.menu_current_status -> {
                toast(p0.title.toString())
            }
            R.id.menu_meeting_days -> {
                toast(p0.title.toString())
            }
            R.id.menu_events -> {
                toast(p0.title.toString())
            }
            R.id.menu_devotion -> {
                toast(p0.title.toString())
            }
            R.id.menu_bible_studies -> {
                toast(p0.title.toString())
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START, true)
        return true
    }
}
